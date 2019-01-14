package com.edallagnol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.io.IOException;

@State(Scope.Benchmark)
public class JacksonCreateObjectMapperTest {
    private TestJson testJson;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String json = "{\"test1\":1,\"test2\":\"aaabbb\"}";

    @Setup
    public void setup() {
        testJson = new TestJson();
        testJson.setTest1(1);
        testJson.setTest2("aaabbb");
    }

    @Benchmark
    public void createWrite() throws JsonProcessingException {
        new ObjectMapper().writeValueAsString(testJson);
    }

    @Benchmark
    public void createRead() throws IOException {
        new ObjectMapper().readValue(json, TestJson.class);
    }

    @Benchmark
    public void reuseWrite() throws JsonProcessingException {
        mapper.writeValueAsString(testJson);
    }

    @Benchmark
    public void reuseRead() throws IOException {
        mapper.readValue(json, TestJson.class);
    }

    public static final class TestJson {
        private int test1;
        private String test2;

        public String getTest2() {
            return test2;
        }

        public void setTest2(String test2) {
            this.test2 = test2;
        }

        public int getTest1() {
            return test1;
        }

        public void setTest1(int test1) {
            this.test1 = test1;
        }
    }
}
