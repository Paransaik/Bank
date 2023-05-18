package com.back.miru.model.dto;

public class ConcernCloud {

	private String name;
	private int weight;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "ConcernCloud [name=" + name + ", weight=" + weight + "]";
	}

}
