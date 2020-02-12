package com.feathercompany.www;



public class DataSet
{
	private String materia;
	private String nota_p1;
	private String nota_p2;
	private String nota_p3;
	private String nota_p4;
	private String nota_rf;
	private String nota_rec;

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getNota_p1() {
		return nota_p1;
	}

	public void setNota_p1(String nota_p1) {
		this.nota_p1 = nota_p1;
	}

	public String getNota_p2() {
		return nota_p2;
	}

	public void setNota_p2(String nota_p2) {
		this.nota_p2 = nota_p2;
	}

	public String getNota_p3() {
		return nota_p3;
	}

	public void setNota_p3(String nota_p3) {
		this.nota_p3 = nota_p3;
	}

	public String getNota_p4() {
		return nota_p4;
	}

	public void setNota_p4(String nota_p4) {
		this.nota_p4 = nota_p4;
	}

	public String getNota_rf() {
		return nota_rf;
	}

	public void setNota_rf(String nota_rf) {
		this.nota_rf = nota_rf;
	}

	public String getNota_rec() {
		return nota_rec;
	}

	public void setNota_rec(String nota_f) {
		this.nota_rec = nota_f;
	}

	public String neeededToApproved(){
		String response = "";
		double n1,n2,n3,n4;
		try{
				n1 = Double.parseDouble(nota_p1);
		}catch (Exception e){
				n1 = 0;
		}
		try{
			n2 = Double.parseDouble(nota_p2);
		}catch (Exception e){
			n2 = 0;
		}
		try{
			n3 = Double.parseDouble(nota_p3);
		}catch (Exception e){
			n3 = 0;
		}
		try{
			n4 = Double.parseDouble(nota_p4);
		}catch (Exception e){
			n4 = 0;
		}
		try{
			double notaFilnal = Double.parseDouble(nota_rf);
			return "Aprovado(a)!";
		}catch (Exception e){
			double necessario = 24 -  ( n1 +n2 + n3 + n4);


			if(necessario<=0){
				response = "Aprovado(a)!";
			}else{
				response = String.valueOf(necessario);
			}
			return response;
		}
	}
}
