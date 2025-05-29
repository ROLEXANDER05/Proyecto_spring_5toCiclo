package com.proy.VIBA.modelo;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tbl_producto")
@Data
public class ClassProducto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idproducto;
	
	@Column(name="nomproducto", nullable=false, length = 50)
	private String nomproducto;
	
	@Column(name="desproducto", nullable=false, length = 500)
	private String desproducto;
	
	@Column(name="preproducto", nullable=false, length = 10)
	private double preproducto;
	
	@Column(name="stockproducto", nullable=false, length = 10)
	private int stockproducto;
	
	@Column(name="catproducto", nullable=false, length = 20)
	private String catproducto;
	
	@Override
	public String toString() {
	    return "ClassProducto [idproducto=" + idproducto + ", nomproducto=" + nomproducto 
	            + ", desproducto=" + desproducto + ", preproducto=" + preproducto 
	            + ", stockproducto=" + stockproducto + ", catproducto=" + catproducto + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Integer getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}

	public String getNomproducto() {
		return nomproducto;
	}

	public void setNomproducto(String nomproducto) {
		this.nomproducto = nomproducto;
	}

	public String getDesproducto() {
		return desproducto;
	}

	public void setDesproducto(String desproducto) {
		this.desproducto = desproducto;
	}

	public double getPreproducto() {
		return preproducto;
	}

	public void setPreproducto(double preproducto) {
		this.preproducto = preproducto;
	}

	public int getStockproducto() {
		return stockproducto;
	}

	public void setStockproducto(int stockproducto) {
		this.stockproducto = stockproducto;
	}

	public String getCatproducto() {
		return catproducto;
	}

	public void setCatproducto(String catproducto) {
		this.catproducto = catproducto;
	}

	
	
	
	
}
