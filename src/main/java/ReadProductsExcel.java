import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class ReadProductsExcel {
    public Product[] getAllProducts(InputStream in) {
        Product[] products = null;
        try {
            XSSFWorkbook xw = new XSSFWorkbook(in);
            XSSFSheet xs = xw.getSheetAt(0);
            products = new Product[xs.getLastRowNum()];
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
//                System.out.println("" + xs.getLastRowNum());
                products[j - 1] = new Product();
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell == null)
                        continue;
                    if (k == 0) {
                        products[j - 1].setProId(this.getValue(cell));
                    } else if (k == 1) {
                        products[j - 1].setProName(this.getValue(cell));
                    } else if (k == 2) {
                        products[j - 1].setPrice(Float.valueOf(this.getValue(cell)));//字符串转float
                    } else if (k == 3) {
                        products[j - 1].setDecription(this.getValue(cell));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return products;
    }

    public Product getProById(String id, InputStream in) {//根据商品id返回商品
        Product product = null;
        try {
            XSSFWorkbook xw = new XSSFWorkbook(in);
            XSSFSheet xs = xw.getSheetAt(0);
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
//                System.out.println("" + xs.getLastRowNum());
                product = new Product();
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell == null)
                        continue;
                    if (k == 0) {
                        product.setProId(this.getValue(cell));
                    } else if (k == 1) {
                        product.setProName(this.getValue(cell));
                    } else if (k == 2) {
                        product.setPrice(Float.valueOf(this.getValue(cell)));//字符串转float
                    } else if (k == 3) {
                        product.setDecription(this.getValue(cell));
                    }
                }
                //如果手动输入的id和produc的id一致，则表示找到了商品，然后返回该商品
                if (id.equals(product.getProId())) {
                    return product;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;//找不到商品时返回null
    }

    private String getValue(XSSFCell cell) {
        String value;
        CellType type = cell.getCellType();

        switch (type) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BLANK:
                value = "";
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            case NUMERIC:
                DecimalFormat decimalFormat = new DecimalFormat("#");
                value = decimalFormat.format(cell.getNumericCellValue());
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case ERROR:
                value = "非法字符";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }
}
