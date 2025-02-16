package com.vitira.treasuryAutomation.dto;

import java.time.LocalDate;

public class InflowsRequest {
	private Long id;
    private LocalDate date;
    private InflowsData inflowsData;
    
 // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public InflowsData getData() {
        return inflowsData;
    }

    public void setData(InflowsData inflowsData) {
        this.inflowsData = inflowsData;
    }

    public class InflowsData {
        private double localCollections;
        private double exportSales;
        private double taxRefunds;
        private double interestReceipts;
        private double loanAvailed;
        private double others;
        private double totalInflows;
        
        

        public InflowsData(double localCollections, double exportSales, double taxRefunds, double interestReceipts,
				double loanAvailed, double others, double totalInflows) {
			super();
			this.localCollections = localCollections;
			this.exportSales = exportSales;
			this.taxRefunds = taxRefunds;
			this.interestReceipts = interestReceipts;
			this.loanAvailed = loanAvailed;
			this.others = others;
			this.totalInflows = totalInflows;
		}

		// Getters and Setters
        public double getLocalCollections() {
            return localCollections;
        }

        public void setLocalCollections(double localCollections) {
            this.localCollections = localCollections;
        }

        public double getExportSales() {
            return exportSales;
        }

        public void setExportSales(double exportSales) {
            this.exportSales = exportSales;
        }

        public double getTaxRefunds() {
            return taxRefunds;
        }

        public void setTaxRefunds(double taxRefunds) {
            this.taxRefunds = taxRefunds;
        }

        public double getInterestReceipts() {
            return interestReceipts;
        }

        public void setInterestReceipts(double interestReceipts) {
            this.interestReceipts = interestReceipts;
        }

        public double getLoanAvailed() {
            return loanAvailed;
        }

        public void setLoanAvailed(double loanAvailed) {
            this.loanAvailed = loanAvailed;
        }

        public double getOthers() {
            return others;
        }

        public void setOthers(double others) {
            this.others = others;
        }

        public double getTotalInflows() {
            return totalInflows;
        }

        public void setTotalInflows(double totalInflows) {
            this.totalInflows = totalInflows;
        }
    }
}