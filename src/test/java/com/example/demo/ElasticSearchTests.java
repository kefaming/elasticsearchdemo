package com.example.demo;

import com.example.demo.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.MultiGetItem;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class ElasticSearchTests {
    @Autowired
    ElasticsearchRestTemplate elasticsearchTemplate;

    // 添加数据
//    @Test
    void contextLoads() {
        // IndexQuery query, IndexCoordinates index
        // 对索引名为teststudent的索引添加数据，如果不存在索引名为teststudent的索引则创建索引之后执行插入，如果存在直接插入
        Student student = new Student(1, "张三", 18);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId("1")
                .withObject(student).build();
        IndexCoordinates indexCoordinates = IndexCoordinates.of("testemp");
        elasticsearchTemplate.index(indexQuery, indexCoordinates);
    }

    // 删除索引中的数据
//    @Test
    void test01() {
        IndexCoordinates indexCoordinates = IndexCoordinates.of("testemp");
        elasticsearchTemplate.delete("1", indexCoordinates);
    }

//    @Test
    void test02() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        NativeSearchQuery query = new NativeSearchQueryBuilder().withIds(strings).build();
        IndexCoordinates indexCoordinates = IndexCoordinates.of("testemp");
        elasticsearchTemplate.delete(query, Student.class, indexCoordinates);
    }

    // 修改数据  更新涉及到版本控制以便维护数据一致性，其实分为两个操作：get和reindex，大致步骤是：首先取到相应的document，然后执行更新script，最后返回执行的结果。至于具体的多版本控制机制将在第6部分解释。
    /**
     @Test
     void test03() {
     //UpdateQuery query, IndexCoordinates index
     Map<String,Object> map=new HashMap<String,Object>();
     map.put("name","王五");
     String script="ctx._source.name='王五'";
     UpdateQuery updateQuery = UpdateQuery.builder("1").withParams(map).withScript(script).build();
     IndexCoordinates indexCoordinates=IndexCoordinates.of("testemp");
     elasticsearchTemplate.update(updateQuery,indexCoordinates);
     }*/
    // 查数据
    @Test
    void test04() {
        //Query query, Class<T> clazz, IndexCoordinates index
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        NativeSearchQuery query = new NativeSearchQueryBuilder().withIds(strings).build();
        IndexCoordinates indexCoordinates = IndexCoordinates.of("testemp");
        List<MultiGetItem<Student>> ts = elasticsearchTemplate.multiGet(query, Student.class, indexCoordinates);
//        for (Student student:ts){
//            System.out.println(student);
//        }

        for(MultiGetItem<Student> m: ts) {


            int j = 1;
        }
        int i = 0;

    }

}
