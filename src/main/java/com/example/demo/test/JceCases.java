package com.example.demo.test;

import com.alibaba.fastjson.JSONObject;
import com.ndsec.jce.model.AsymmetricParams;
import com.ndsec.jce.model.SM2PrivateKey;
import com.ndsec.jce.model.SM2PublicKey;
import com.ndsec.jce.model.SymmetricParams;
import com.ndsec.jce.provider.NDSecProvider;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JceCases {
    static class Pair<T1, T2> {
        T1 first;
        T2 second;

        public Pair(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

    public static byte[] convert_to_bytes(String s) {
        if (s == null || s.length() % 2 != 0) {
            throw new RuntimeException("invalid");
        }

        byte[] result = new byte[s.length() / 2];
        for (int i = 0; i < result.length; ++i) {
            result[i] =
                    (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }
        return result;
    }

    public static String convert_to_string(byte[] data) {
        final String map = "0123456789abcdef";

        StringBuilder sb = new StringBuilder();
        for (byte i : data) {
            int hi = (i >> 4) & 0xf;
            int lo = i & 0xf;
            sb.append(map.charAt(hi));
            sb.append(map.charAt(lo));
        }
        return sb.toString();
    }

    public static void symmetric_test(Provider provider, String algorithm)
            throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("RND", provider);

        byte[] plain = new byte[16];
        secureRandom.nextBytes(plain);

        ArrayList<Pair<Key, SymmetricParams>> keys = new ArrayList<>();
        // internal key
        {
            SymmetricParams params = new SymmetricParams();
            params.setInternalKeyIndex(1);
            keys.add(new Pair<>(null, params));
            // cipher.init(Cipher.ENCRYPT_MODE, null, params);
        }
        // external key
        {
            byte[] key_data = new byte[16];
            secureRandom.nextBytes(key_data);
            SecretKey key = new SecretKeySpec(key_data, algorithm);
            keys.add(new Pair<>(key, new SymmetricParams()));
            // cipher.init(Cipher.ENCRYPT_MODE, key, null);
        }
        // generated key
        // {
        //     KeyGenerator keygen = KeyGenerator.getInstance("SM4", provider);
        //     keygen.init(128, secureRandom);
        //     keys.add(new Pair<>(keygen.generateKey(), null));
        // }

        for (String mode : Arrays.asList("ECB", "CBC", "OFB")) {
            for (Pair<Key, SymmetricParams> key : keys) {
                Cipher cipher = Cipher.getInstance(
                        String.format("%s/%s/NoPadding", algorithm, mode),
                        provider);

                // set iv
                byte[] iv = new byte[16];
                secureRandom.nextBytes(iv);
                key.second.setIV(iv);

                // encrypt
                cipher.init(Cipher.ENCRYPT_MODE, key.first, key.second);
                byte[] encrypts = cipher.doFinal(plain);

                // decrypt
                cipher.init(Cipher.DECRYPT_MODE, key.first, key.second);
                byte[] decrypts = cipher.doFinal(encrypts);

                if (!Arrays.equals(plain, decrypts)) {
                    throw new RuntimeException(
                            String.format("%s/%s result is differ", algorithm,
                                    mode));
                }
            }
        }

        // cbc mac
        {
            byte[] data = new byte[1024];
            secureRandom.nextBytes(data);

            Mac mac = Mac.getInstance(algorithm, provider);
            for (Pair<Key, SymmetricParams> key : keys) {
                mac.init(key.first, key.second);
                mac.update(data);
                byte[] macResult = mac.doFinal();
                if (macResult.length != mac.getMacLength()) {
                    throw new RuntimeException(
                            String.format("%s/MAC is failed", algorithm));
                }
            }
        }

        System.out.println(algorithm + " test passed");
    }

    public static void sm3_hash(Provider provider) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("RND", provider);

        MessageDigest sm3 = MessageDigest.getInstance("SM3", provider);
        for (int i = 0; i < 10; ++i) {
            byte[] plain = new byte[2048];
            secureRandom.nextBytes(plain);
            sm3.update(plain);
        }
        byte[] digest = sm3.digest();
        if (digest.length != sm3.getDigestLength()) {
            throw new RuntimeException("SM3 digest is failed");
        }

        System.out.println("SM3 digest test passed");
    }

    public static void sm2_encrypt_decrypt(Provider provider) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("RND", provider);

        byte[] plain = new byte[47];
        secureRandom.nextBytes(plain);

        ArrayList<Pair<KeyPair, AsymmetricParams>> keys = new ArrayList<>();
        // internal keypair
        {
            AsymmetricParams params = new AsymmetricParams();
            params.setInternalKeyIndex(1);
            keys.add(new Pair<>(null, params));
        }
        // external keypair
        {
            byte[] x = convert_to_bytes(
                    "bada5907d718bdea8990e5176e504ccd65ae6a7783508fb21d6e99f513386eda");
            byte[] y = convert_to_bytes(
                    "5c3acd4f2a1a4c8ea65aa0a453ef60e40bca672104fc0c0442aac9d580a229e5");
            byte[] d = convert_to_bytes(
                    "bd8aa19bd5fa0d33840eaacef4692430849d77afab4381b59bb7e5e5dc81a425");
            SM2PublicKey publicKey = new SM2PublicKey(x, y);
            SM2PrivateKey privateKey = new SM2PrivateKey(d);
            keys.add(new Pair<>(new KeyPair(publicKey, privateKey), null));
        }
        // generated keypair
        {
            KeyPairGenerator keygen =
                    KeyPairGenerator.getInstance("SM2", provider);
            keygen.initialize(256);
            keys.add(new Pair<>(keygen.genKeyPair(), null));
        }

        for (Pair<KeyPair, AsymmetricParams> key : keys) {
            Cipher cipher = Cipher.getInstance("SM2", provider);
            // encrypt
            cipher.init(Cipher.ENCRYPT_MODE,
                    key.first == null ? null : key.first.getPublic(),
                    key.second);
            byte[] encrypts = cipher.doFinal(plain);

            // decrypt
            cipher.init(Cipher.DECRYPT_MODE,
                    key.first == null ? null : key.first.getPrivate(),
                    key.second);
            byte[] decrypts = cipher.doFinal(encrypts);

            if (!Arrays.equals(plain, decrypts)) {
                throw new RuntimeException(
                        "SM2 encrypt & decrypt diff is failed");
            }
        }

        System.out.println("SM2 encrypt & decrypt test passed");
    }

    public static void sm2_sign_verify(Provider provider) throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("RND", provider);

        byte[] plain = new byte[32];
        secureRandom.nextBytes(plain);

        ArrayList<Pair<KeyPair, AsymmetricParams>> keys = new ArrayList<>();
        // internal keypair
        {
            AsymmetricParams params = new AsymmetricParams();
            params.setInternalKeyIndex(1);
            keys.add(new Pair<>(null, params));
        }
        // external keypair
        {
            byte[] x = convert_to_bytes(
                    "bada5907d718bdea8990e5176e504ccd65ae6a7783508fb21d6e99f513386eda");
            byte[] y = convert_to_bytes(
                    "5c3acd4f2a1a4c8ea65aa0a453ef60e40bca672104fc0c0442aac9d580a229e5");
            byte[] d = convert_to_bytes(
                    "bd8aa19bd5fa0d33840eaacef4692430849d77afab4381b59bb7e5e5dc81a425");
            SM2PublicKey publicKey = new SM2PublicKey(x, y);
            SM2PrivateKey privateKey = new SM2PrivateKey(d);
            keys.add(new Pair<>(new KeyPair(publicKey, privateKey), null));
        }
        // generated keypair
        {
            KeyPairGenerator keygen =
                    KeyPairGenerator.getInstance("SM2", provider);
            keygen.initialize(256);
            keys.add(new Pair<>(keygen.genKeyPair(), null));
        }

        for (String mode : Arrays.asList("SM2", "SM3WithSM2")) {
            for (Pair<KeyPair, AsymmetricParams> key : keys) {
                Signature signature = Signature.getInstance(mode, provider);
                // sign
                signature.initSign(
                        key.first == null ? null : key.first.getPrivate());
                signature.setParameter(key.second);
                signature.update(plain);
                byte[] sign_result = signature.sign();

                // verify
                signature.initVerify(
                        key.first == null ? null : key.first.getPublic());
                signature.setParameter(key.second);
                signature.update(plain);

                if (!signature.verify(sign_result)) {
                    throw new RuntimeException("SM2 sign & verify is failed");
                }
            }
        }

        System.out.println("SM2 sign & verify test passed");
    }

    public static void main(String[] args) throws Exception {
        // set sdf config before loading com.ndsec.jce provider
        {
            JSONObject nativeConfig = new JSONObject();
            nativeConfig.put("device_type", "rpc");
            nativeConfig.put("device_socket", "172.16.111.74:5000");

//            List<JSONObject> slaves = new ArrayList<>();
//            // add a slave
//            {
//                JSONObject slaveConfig = new JSONObject();
//                slaveConfig.put("device_type", "rpc");
//                slaveConfig.put("device_socket", "ip:port_of_slave");
//                slaves.add(slaveConfig);
//            }
//            nativeConfig.put("slaves", slaves);

            System.out.println(nativeConfig);
            NDSecProvider.getSettings()
                    .setNativeSocket(nativeConfig.toString());
        }

        // load jce provider dynamically
        NDSecProvider provider = new NDSecProvider();
        Security.addProvider(provider);
        // or, use provider name
        // String provider = NDSecProvider.getProviderName();

        SecureRandom secureRandom = SecureRandom.getInstance("RND", provider);
        // get random data
        {
            byte[] random = new byte[16];
            secureRandom.nextBytes(random);
            System.out.println("random: " + Arrays.toString(random));
        }

        // symmetric algorithms
        symmetric_test(provider, "SM1");
        symmetric_test(provider, "SM4");

        // hash
        sm3_hash(provider);

        // sm2 encrypt & decrypt
        sm2_encrypt_decrypt(provider);

        // sm2 sign & verify
        sm2_sign_verify(provider);
    }
}
