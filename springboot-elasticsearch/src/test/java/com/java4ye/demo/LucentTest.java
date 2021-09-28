package com.java4ye.demo;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
public class LucentTest {

    User createUser(){
        User java4ye = User.builder()
                .id(12345678)
                .name("Java4ye")
                .age(1)
                .desc("期待你的关注!").build();
        return java4ye;
    }


    /**
     * 1. 采集数据
     * 2. 创建文档对象
     * 3. 创建分词器
     * 4. 创建 Directory  目录对象，对象表示索引库的位置
     * 5. 创建 IndexWriterConfig 对象，指定使用的分词器
     * 6. 创建 IndexWriter 输出流对象，指定输出位置和使用的 config 初始化对象
     * 7. 写入文档到索引库
     * 8. 释放资源
     * @throws IOException
     */
    @Test
    void createIndex() throws IOException {
        // 1. 获取 原始数据
        User user = createUser();
        // 2. 创建 文档对象
        Document document = new Document();

        // 添加 文本域

        // StringField
        // 分词：no
        // 索引：yes
        // 存储：yes
        document.add(new StringField("id",String.valueOf(user.getId()), Field.Store.YES));

        // TextField
        // 分词：yes
        // 索引：yes
        // 存储：yes
        document.add(new TextField("age",String.valueOf(user.getAge()), Field.Store.YES));

        document.add(new StringField("name",user.getName(), Field.Store.YES));

        document.add(new TextField("desc",user.getDesc(), Field.Store.YES));


        // 3. 创建 分词器
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();

        // 4. 获取
        Directory directory = FSDirectory.open(Path.of("D:\\luceneIdx"));

        // 5.
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(standardAnalyzer);

        // 6
        try (IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig)) {
          indexWriter.addDocument(document);
        }

    }


    /**
     * 1. 创建分词器（对搜索的关键词进行分词使用）和创建索引使用的分词器一样
     *
     * 2. 创建查询对象
     * 3. 设置搜索关键词
     * 4. 设置 Directory 目录对象，指定索引库的位置
     * 5. 创建输入流对象
     * 6. 创建搜索对象
     * 7. 搜索，并返回结果
     * 8. 获取结果集
     * 9. 遍历结果集
     * 10. 关闭流
     */
    @Test
    void search() throws IOException, ParseException, NoSuchFieldException, IllegalAccessException {

        User user = createUser();

        // 1. 创建 分词器
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();


        String field="desc";
        Class<? extends User> aClass = user.getClass();
        java.lang.reflect.Field declaredField = aClass.getDeclaredField(field);
        declaredField.setAccessible(true);


        String content=declaredField.get(user)+"";

        // 创建查询对象
        QueryParser queryParser = new QueryParser(field, standardAnalyzer);
        // 设置搜索关键词
        Query parse = queryParser.parse(content);

        // 设置 Directory 目录对象，指定索引库的位置
        Directory directory = FSDirectory.open(Path.of("D:\\luceneIdx"));

        IndexReader r=DirectoryReader.open(directory);

        IndexSearcher indexSearcher = new IndexSearcher(r);

        TopDocs topDocs = indexSearcher.search(parse, 10);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println(scoreDoc.doc);
            System.out.println(scoreDoc.shardIndex);
            System.out.println(scoreDoc.score);
            System.out.println(scoreDoc.toString());
            System.out.println(r.document(scoreDoc.doc));
            System.out.println(r.document(scoreDoc.doc).get(field));
            System.out.println("===============");

        }

        directory.close();

    }

}
