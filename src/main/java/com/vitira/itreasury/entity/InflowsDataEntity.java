package com.vitira.itreasury.entity;

public class InflowsDataEntity {

	private double localCollections;
	private double exportSales;
	private double taxRefunds;
	private double interestReceipts;
	private double loanAvailed;
	private double others;
	private double totalInflows;

	// Note: Default constructor for deserization working correctly
	public InflowsDataEntity() {

	}

	public InflowsDataEntity(double localCollections, double exportSales, double taxRefunds, double interestReceipts,
			double loanAvailed, double others) {
		super();
		this.localCollections = localCollections;
		this.exportSales = exportSales;
		this.taxRefunds = taxRefunds;
		this.interestReceipts = interestReceipts;
		this.loanAvailed = loanAvailed;
		this.others = others;
		updateTotalInflows();
	}

	public double getLocalCollections() {
		return localCollections;
	}

	public void setLocalCollections(double localCollections) {
		this.localCollections = localCollections;
		updateTotalInflows();
	}

	public double getExportSales() {
		return exportSales;
	}

	public void setExportSales(double exportSales) {
		this.exportSales = exportSales;
		updateTotalInflows();
	}

	public double getTaxRefunds() {
		return taxRefunds;
	}

	public void setTaxRefunds(double taxRefunds) {
		this.taxRefunds = taxRefunds;
		updateTotalInflows();
	}

	public double getInterestReceipts() {
		return interestReceipts;
	}

	public void setInterestReceipts(double interestReceipts) {
		this.interestReceipts = interestReceipts;
		updateTotalInflows();
	}

	public double getLoanAvailed() {
		return loanAvailed;
	}

	public void setLoanAvailed(double loanAvailed) {
		this.loanAvailed = loanAvailed;
		updateTotalInflows();
	}

	public double getOthers() {
		return others;
	}

	public void setOthers(double others) {
		this.others = others;
		updateTotalInflows();
	}

	public double getTotalInflows() {
		return totalInflows;
	}

	private void updateTotalInflows() {
		this.totalInflows = localCollections + exportSales + taxRefunds + interestReceipts + loanAvailed + others;
	}
}
