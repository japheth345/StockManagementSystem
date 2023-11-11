package attr;

import java.lang.*;
import java.util.*;
import java.sql.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.*;
import attr.*;
import activity.*;
import javax.swing.*;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;
public class Product {
	private String productId;
	private String productName;
	private double price;
	private int quantity;
	public static String[] columnNames = {"PID", "Name", "Price", "AvailableQuantity"};
        public static String[] columnNames2 = {"PID", "Customer ID", "Product ID", "Total","Quantity","Date"};
	 private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
	public Product() {}
	public Product(String productId) {
		if (!productId.isEmpty())
			this.productId = productId;
		else
			throw new IllegalArgumentException("Fill in the ID");
	}
	
	public void setProductName(String name) {
		if (!name.isEmpty())
			this.productName = name;
		else
			throw new IllegalArgumentException("Fill in the name");
	}
	public void setPrice(double p) {
		this.price = p;
	}
	public void setQuantity(int q) {
		this.quantity = q;
	}
	public String getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public double getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	
	public void fetch() {
		String query = "SELECT `productId`, `productName`, `price`, `quantity` FROM `product` WHERE productId='"+this.productId+"';";     
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				this.productName = rs.getString("productName");
				this.price = rs.getDouble("price");
				this.quantity = rs.getInt("quantity");
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
	
	public void sellProduct(String uid, int amount) {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		String query = "INSERT INTO `purchaseInfo` (`userId`, `productId`, `amount`, `date`, `cost`) VALUES ('"+uid+"','"+this.productId+"',"+amount+", '"+date+"', "+(amount*this.price)+");";
		Connection con = null;
        Statement st = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			st.execute(query);//insert
			System.out.println("data inserted");
                        java.util.ArrayList<String> customer=getCustomerDetails(uid);
                         java.util.ArrayList<String> pro=getProductDetails(this.productId);
                         System.out.println("PRODUCT="+this.productId);
                         System.out.println(pro.get(0));
                         String cid=customer.get(0);
                         String cname=customer.get(1);
                         String cphone=customer.get(2);
                           
                       // String pid=pro.get(0);
                         String pname=pro.get(0);
                         String qty=String.valueOf(amount);
                         Double  price=Double.parseDouble(pro.get(2));
                         Double totalCost=price * amount;
                         String cost=String.valueOf(totalCost);
                        
              String desktopPath = System.getProperty("user.home") + "/Desktop";
              String FILE = desktopPath+"/"+cname+".pdf";
                  
                       java.util.ArrayList<String> res=new java.util.ArrayList<>();
                       res.add(cid);
                       res.add(cname);
                       res.add(cphone);
                 
                       res.add(pname);
                        res.add(qty);
                       res.add(cost);
                       
             try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
           
            addContent(document,res);
            document.close();
            File pdfFile = new File(FILE);
        
		
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdfFile);
			

		}
             else {
			System.out.println("File is not exists!");
		}
        } catch (Exception e) {
            e.printStackTrace();
        }
     
                        
			updateProduct(this.productName, this.price, this.quantity-amount);
		}
        catch(Exception ex) {
			//JOptionPane.showMessageDialog(null,"Customer doesn't exist!"); 
                        ex.printStackTrace();
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
	
	public void updateProduct(String name, double price, int quantity) {
		String query = "UPDATE `product` SET `productName`='"+name+"', `price`="+price+", `quantity`="+quantity+" WHERE `productId`='"+this.productId+"';";
		Connection con = null;
        Statement st = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			st.executeUpdate(query);//insert
			System.out.println("data inserted");
			JOptionPane.showMessageDialog(null,"Done!");
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Failed!");
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
	
	public void createProduct() {
		String query = "INSERT INTO `product` (`productName`, `price`, `quantity`) VALUES ('"+productName+"','"+price+"','"+quantity+"');";
		Connection con = null;
        Statement st = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			st.execute(query);//insert
			System.out.println("data inserted");
			JOptionPane.showMessageDialog(null,"Product Created!");
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Failed to add Product!");
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
	
	public static DefaultTableModel searchProduct(String keyword, String byWhat) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);
		String query = "SELECT `productId`, `productName`, `price`, `quantity` FROM `product` WHERE `productId`='"+keyword+"';";
		if (byWhat.equals("By Name"))
			query = "SELECT `productId`, `productName`, `price`, `quantity` FROM `product` WHERE `productName` LIKE '%"+keyword+"%';";
		else {}
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				model.addRow(new Object[]{rs.getString("productId"), rs.getString("productName"), rs.getDouble("price"), rs.getInt("quantity")});
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
		return model;
	}
	public static DefaultTableModel searchProduct2(String keyword, String byWhat) {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames2);
		String query = "SELECT `purchaseId`, `userId`,`productId`,`cost`,`amount`, `date` FROM `purchaseInfo` WHERE `productId`='"+keyword+"';";
		if (byWhat.equals("By Date"))
			query = "SELECT `purchaseId`, `userId`,`productId`,`cost`,`amount`, `date` FROM `purchaseInfo` WHERE `date` LIKE '%"+keyword+"%';";
                if (byWhat.equals("By ProductID"))
			query = "SELECT `purchaseId`, `userId`,`productId`,`cost`,`amount`, `date` FROM `purchaseInfo` WHERE `productId` LIKE '%"+keyword+"%';";
		else {}
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			rs = st.executeQuery(query);//getting result
			System.out.println("results received");
			
			while(rs.next()) {
				model.addRow(new Object[]{rs.getString("productId"), rs.getString("userId"),rs.getString("productId"), rs.getDouble("cost"), rs.getInt("amount"),rs.getString("date"),});
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
		return model;
	}
	public void deleteProduct() {
		String query1 = "DELETE FROM `product` WHERE `productId`='"+this.productId+"';";
		Connection con = null;
        Statement st = null;
		System.out.println(query1);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			System.out.println("connection done");//connection with database established
			st = con.createStatement();//create statement
			System.out.println("statement created");
			st.execute(query1);//delete
			System.out.println("data deleted");
			JOptionPane.showMessageDialog(null,"Product Deleted!");
		}
        catch(Exception ex) {
			JOptionPane.showMessageDialog(null,"Failed to delete product!");
			System.out.println("Exception : " +ex.getMessage());
        }
        finally {
            try {
                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
	}
        public java.util.ArrayList<String> getProductDetails(String id)
        {
            
       java.util.ArrayList<String> res=new java.util.ArrayList<String>();
       
            try{
            Class.forName("com.mysql.jdbc.Driver");
	System.out.println("driver loaded");
	Connection con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
        String query = "SELECT `productId`, `productName`, `price`, `quantity` FROM `product` WHERE `productId`='"+id+"';";
       Statement st = con.createStatement();//create statement
	
	ResultSet rs = st.executeQuery(query);//getting result
			
			
			while(rs.next()) {
				res.add(rs.getString("productName"));
                                res.add(rs.getString("productName"));
                                res.add(String.valueOf(rs.getDouble("price")));
                                       
			}
        
        }
            catch(Exception ex)
            {
                ex.printStackTrace();
                res.add("No data");
            }
            return res;
}
    public java.util.ArrayList<String> getCustomerDetails(String userId)
            
    {
        java.util.ArrayList<String> res=new java.util.ArrayList<String>();
    
        String query = "SELECT `userId`, `customerName`, `phoneNumber`, `address` FROM `customer` WHERE userId='"+userId+"';";     
        Connection con = null;
        Statement st = null;
		ResultSet rs = null;
		System.out.println(query);
        try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("driver loaded");
			con = DriverManager.getConnection(Database.HOST_URI, Database.USER, Database.PASSWORD);
			
			st = con.createStatement();//create statement
			
			rs = st.executeQuery(query);//getting result
			
			
			while(rs.next()) {
                            
                                res.add(rs.getString("userid"));
				res.add(rs.getString("customerName"));
				res.add(rs.getString("phoneNumber"));
				
			}
		}
        catch(Exception ex) {
			System.out.println("Exception : " +ex.getMessage());
                        res.add("No data");
        }
        finally {
            try {
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex) {}
        }
        return res;
    }
    private static void addMetaData(Document document) {
        document.addTitle("JANYA SUPERMARKET");
      /*  document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
*/
    }

   

    private static void addContent(Document document,java.util.ArrayList<String> res) throws DocumentException {
        Anchor anchor = new Anchor("STOCK MANAGEMENT SYSTEM", catFont);
        anchor.setName("STOCK MANAGEMENT SYSTEM");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Customer Receipt", subFont);
        Section subCatPart = catPart.addSection(subPara);
       // subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Details", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("DATE:\t"+ new java.util.Date().toString()));
        subCatPart.add(new Paragraph("CUSTOMER ID: \t"+res.get(0)));
        subCatPart.add(new Paragraph("CUSTOMER NAME:\t"+res.get(1)));
        subCatPart.add(new Paragraph("CUSTOMER PHONE:\t"+res.get(2)));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart,res);

        // now add all this to the document
        document.add(catPart);

        // Next section
       // anchor = new Anchor("Second Chapter", catFont);
       // anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        
        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart,java.util.ArrayList<String> res)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("PRODUCT NAME"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("QUANTITY"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("TOTAL(Kshs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell(res.get(3));
        table.addCell(res.get(4));
        table.addCell(res.get(5));
        

        subCatPart.add(table);
         subCatPart.add(new Paragraph("Signature................................................", subFont));
        subCatPart.add(new Paragraph("Thank you \n Welcome again", subFont));
                 
    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        //list.add(new ListItem("First point"));
       // list.add(new ListItem("Second point"));
        //list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}

        