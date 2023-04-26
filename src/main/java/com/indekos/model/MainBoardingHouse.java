package com.indekos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor @NoArgsConstructor
public class MainBoardingHouse {
	
	@Id
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String address;
	
	private Integer RT;
	private Integer RW;
	
	@Column(nullable = false)
	private String subdistrict;		// Kelurahan
	
	@Column(nullable = false)
	private String district;		// Kecamatan
	
	@Column(nullable = false)
	private String cityOrRegency;	// Kota/ kecamatan
	
	@Column(nullable = false)
	private String province;		// Provinsi
	
	@Column(nullable = false)
	private String country;			// Negara
	
	@Column(nullable = false)
	private Integer postalCode;		// Kode pos
	
	@Column(nullable = false)
	private String rentPaymentType;						// Prabayar (Awal periode) / Pascabayar (Akhir periode)
	
	@Column(nullable = false)
	private String rentPaymentTypePeriod;				// Bulanan (Monthly)/ Tahunan (Annual)
	
	@Column(nullable = false)
	private Integer dueDatePerPeriod;					// Tanggal jatuh tempo pembayaran: 1/ 2/ 3/ dll.
	
	private Integer dueDateMonthForAnnual;				// Bulan jatuh tempo pembayaran untuk pembayaran sewa tahunan: Januari, Februari, dll.
	
	@Column(nullable = false)
	private Integer toleranceOverduePaymentInDays;		// Toleransi keterlambatan pembayaran dalam satuan hari: 1/ 2/ 3/ dll.
	
	@Column(nullable = false)
	private Integer penaltyFeePerOverduePaymentInDays;	// Denda yang dikenakan per hari keterlambatan dalam rupiah
	
//	@ManyToOne
//	@JoinColumn(name="owner_id", referencedColumnName = "id")
//	private User owner;		// User ID pemilik
}