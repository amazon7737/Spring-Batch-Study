package org.schooldevops.springbatch.sample.domain;

public class CompositeIndex {
    private String index;
    private Double number;

    public void setIndex(String index){
        this.index = index;
    }

    public void setNumber(Double number){
        this.number = number;
    }

    public String getIndex(){
        return this.index;
    }

    public Double getNumber(){
        return this.number;
    }
}
