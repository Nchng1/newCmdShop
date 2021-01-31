import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class ReadUsersExcel {
    public User[] getAllUsers(InputStream in){
        User[] users = null;
        try {
            XSSFWorkbook xw = new XSSFWorkbook(in);
            XSSFSheet xs = xw.getSheetAt(0);
            users = new User[xs.getLastRowNum()];
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
//                System.out.println("" + xs.getLastRowNum());
                users[j-1] = new User();
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if(cell == null)
                        continue;
                    if(k == 0){
                        users[j-1].setName(this.getValue(cell));
                    }else if(k == 1){
                        users[j-1].setPassword(this.getValue(cell));
                    }else if(k == 2){
                        users[j-1].setAddress(this.getValue(cell));
                    }else if(k == 3){
                        users[j-1].setPhone(this.getValue(cell));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return users;
    }

    private String getValue(XSSFCell cell){
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
