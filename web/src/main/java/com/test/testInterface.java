package com.test;


public class testInterface {
	public static void main(String[] args) {
		new demo01(new version(){
			public String getName() {
				return "my";
			}
			public String getVersion() {
				return "0.1";
			}
		}, new age(){
			public String getAge() {
				return "1";
			}
		}).execute();
		
		new demo01(new version(){
			public String getName() {
				return "your";
			}
			public String getVersion() {
				return "0.2";
			}
		}, new age(){
			public String getAge() {
				return "2";
			}
		}).execute();
		
		version hedemo02 = new hedemo01();
		age age = new ageChild();
		demo01 demo01 = new demo01(hedemo02,age);
		demo01.execute();
		
		version heChildDemo01 = new heChildDemo01();
		demo01 = new demo01(heChildDemo01,age);
		demo01.execute();
	}
}

class hedemo01 implements version,age{
	@Override
	public String getVersion() {
		return "0.3";
	}

	@Override
	public String getName() {
		return "his";
	}

	@Override
	public String getAge() {
		return "1";
	}
}

class ageChild implements age{
	@Override
	public String getAge() {
		return "3";
	}
}

class heChildDemo01 extends hedemo01{
}

class demo01{
	
	private version version;
	
	private age age;
	
	public demo01(version version,age age){
		this.version = version;
		this.age = age;
	}
	
	public void execute(){
		String demo=version.getName()+"_"+"demo01"+"-"+version.getVersion() + ":" + age.getAge() +"岁";
		System.out.println(demo);
	}
}

interface version{
	public String getVersion();
	public String getName();
}

interface age{
	public String getAge();
}