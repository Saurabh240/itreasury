package com.vitira.itreasury.entity;

public class OutflowsDataEntity {

	private double localPurchases;
	private double imports;
	private double financeCost;
	private double repaymentOfLoans;
	private double salaries;
	private double directTaxes;
	private double indirectTaxes;
	private double others;
	private double totalOutflows;

	// Note: Default constructor for deserization working correctly
	public OutflowsDataEntity() {

	}

	public OutflowsDataEntity(double localPurchases, double imports, double financeCost, double repaymentOfLoans,
			double salaries, double directTaxes, double indirectTaxes, double others, double totalOutflows) {
		super();
		this.localPurchases = localPurchases;
		this.imports = imports;
		this.financeCost = financeCost;
		this.repaymentOfLoans = repaymentOfLoans;
		this.salaries = salaries;
		this.directTaxes = directTaxes;
		this.indirectTaxes = indirectTaxes;
		this.others = others;

		updateTotalInflows();
	}

	public double getLocalPurchases() {
		return localPurchases;
	}

	public void setLocalPurchases(double localPurchases) {
		this.localPurchases = localPurchases;
		updateTotalInflows();
	}

	public double getImports() {
		return imports;
	}

	public void setImports(double imports) {
		this.imports = imports;
		updateTotalInflows();
	}

	public double getFinanceCost() {
		return financeCost;
	}

	public void setFinanceCost(double financeCost) {
		this.financeCost = financeCost;
		updateTotalInflows();
	}

	public double getRepaymentOfLoans() {
		return repaymentOfLoans;
	}

	public void setRepaymentOfLoans(double repaymentOfLoans) {
		this.repaymentOfLoans = repaymentOfLoans;
		updateTotalInflows();
	}

	public double getSalaries() {
		return salaries;
	}

	public void setSalaries(double salaries) {
		this.salaries = salaries;
		updateTotalInflows();
	}

	public double getDirectTaxes() {
		return directTaxes;
	}

	public void setDirectTaxes(double directTaxes) {
		this.directTaxes = directTaxes;
		updateTotalInflows();
	}

	public double getIndirectTaxes() {
		return indirectTaxes;
	}

	public void setIndirectTaxes(double indirectTaxes) {
		this.indirectTaxes = indirectTaxes;
		updateTotalInflows();
	}

	public double getOthers() {
		return others;
	}

	public void setOthers(double others) {
		this.others = others;
		updateTotalInflows();
	}

	public double getTotalOutflows() {
		return totalOutflows;
	}

	private void updateTotalInflows() {
		this.totalOutflows = localPurchases + imports + financeCost + repaymentOfLoans + salaries + directTaxes
				+ indirectTaxes + others;
	}
}
