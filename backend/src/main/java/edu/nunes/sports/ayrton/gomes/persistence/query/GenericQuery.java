package edu.nunes.sports.ayrton.gomes.persistence.query;

import java.util.HashMap;
import java.util.Map;

public class GenericQuery {

    private String queryName;
    private Map<String, Object> params;
    private Map<String, String> queries;
    private int page;
    private int size;

    protected GenericQuery (){
        this.queryName = "";
        this.page = 1;
        this.size = Integer.MAX_VALUE;
        this.params = new HashMap<>();
        this.queries = new HashMap<>();
    }

    public GenericQuery name(String name){
        this.queryName = name;
        return this;
    }

    public GenericQuery addParam(String key, Object value){
        this.params.put(key, value);
        return this;
    }

    public GenericQuery page(int page){
        this.page = page;
        return this;
    }

    public GenericQuery size(int size){
        this.size = size;
        return this;
    }

    public String getQueryName() {
        return queryName;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    protected void addQuery(String queryName, String query){
        this.queries.put(queryName, query);
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getQuery(){
        return this.queries.get(this.queryName);
    }

    public String getCountQuery(){
        return this.queries.get(this.queryName+".count");
    }

}
