package com.example.Spring_boot_2.enums;

public class DownloadCustomerEnum {
    public  enum customerSheet{
        CustomerID("Customer ID","", 0),
        CustomerName("Name","",1),
        Region("Region","",2),
        Gender("Gender","",3);
        private String columName;
        private  String defaultValue;
        private  int columnIndex;
        customerSheet(String columName, String defaultValue,int columnIndex) {
            this.columName = columName;
            this.defaultValue =defaultValue;
            this.columnIndex = columnIndex;
        }
        public String getColumName(){return columName;}
        public  String getDefaultValue(){return defaultValue;}
        public  int getColumnIndex(){return columnIndex;}

        public  void setColumName( String columName){
            this.columName = columName;
        }
        public  void setDefaultValue( String defaultValue){
            this.defaultValue = defaultValue;
        }
        public  void setColumnIndex( int columnIndex){
            this.columnIndex = columnIndex;
        }


    }
}
