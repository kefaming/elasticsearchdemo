package com.example.demo.service.impl;

import com.example.demo.mapper.ProductDao;
import com.example.demo.model.Product;
import com.example.demo.service.SearchService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private ProductDao productDao;

    @Override
    public List<Product> searchProducts(String query, Integer page, Integer rows) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(query, "productName", "productCategory", "productDescription"))
                .withPageable(PageRequest.of(page - 1, rows))
                .build();
        SearchHits<Product> hits = elasticsearchRestTemplate.search(searchQuery, Product.class);
        List<Product> list = new LinkedList<>();
        Iterator<SearchHit<Product>> iterator = hits.iterator();
        while(iterator.hasNext()) {
            SearchHit hit = iterator.next();
            list.add((Product) hit.getContent()); ;
        }
        return list;
    }

    @Override
    public void createIndex(String indexName, Class<? extends Object> type) {
        IndexOperations ops = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName));
        if (ops.exists()) ops.delete();
        ops.create();
        ops.refresh();
        ops.putMapping(ops.createMapping(type));
    }

//    @Override
//    public void createIndex(Class<? extends Object> type) {
//        IndexOperations ops = elasticsearchRestTemplate.indexOps(type);
//        if (ops.exists()) ops.delete();
//        ops.create();
//        ops.refresh();
//        ops.putMapping(ops.createMapping());
//    }

    public Boolean isIndexExist(String indexName) {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName));
        return indexOps.exists();
    }

    @Override
    public Boolean deleteIndexByName(String indexName) {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName));
        return indexOps.delete();
    }

//    @Override
//    public void deleteIndex(Class<?> type) {
//        elasticsearchRestTemplate.indexOps(type).delete();
//    }

    @Override
    public void deleteProduct(Product product){
        elasticsearchRestTemplate.delete(product.getProductId(), Product.class);
    }

    @Override
    public void addProducts(List<Product> products) {
        this.productDao.saveAll(products);
    }

    @Override
    public void addProduct(Product product) {
        this.productDao.save(product);
    }


}
