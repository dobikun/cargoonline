package cargo.web.controllers;

import cargo.web.beans.Cargo;
import cargo.web.db.Database;
import cargo.web.enums.SearchType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Roman
 */
@ManagedBean(eager = true)
@SessionScoped
public class SearchController implements Serializable{
    
    private boolean requestFromPager;
    private boolean requestFromSearchPager;
    private int booksOnPage = 2;
    private SearchType searchType;
    private static Map<String, SearchType> searchList = new HashMap<String, SearchType>();
    private ArrayList<Cargo> currentCargoList;
    private ArrayList<Cargo> currentCargoSearchList;
    private String searchString; //value in search text
    private char searchLetter; //Selected letter
    private Integer typeId; //typeList 
    private int totalCount; // total cargo entries
    private int totalSearchCount;

    private ArrayList<Integer> pageNumbers = new ArrayList<Integer>(); // total pagination numbers
    private ArrayList<Integer> pageSearchNumbers = new ArrayList<Integer>();
    private String currentSql;// last sql query without limit
    private String currentSearchSql;// last search sql query without limit
    private long selectedPageNumber = 1; // selected page namber in pagination
    private long selectedSearchPageNumber = 1;

    public SearchController() {
        cargoAll();
    }
    
    public void renewCurrentCargoList(){
        if(currentCargoSearchList == null){
            //Fill full cargo list 
            cargoAll();
        } else {
            //Clear entry for search result 
            currentCargoList = new ArrayList<Cargo>();
        }
    }
    
    private void cargoTableSQL(String sql) {
        
        //System.out.print(sql);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        currentSql = sql;

        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            
            System.out.println(requestFromPager);
            //if (!requestFromPager) {
                rs = stmt.executeQuery(sqlBuilder.toString());
                rs.last();

                totalCount = rs.getRow();
                fillPageNumbers(totalCount, booksOnPage);
            //}

            if (totalCount > booksOnPage) {
                 sqlBuilder.append(" limit ").append(selectedPageNumber * booksOnPage - booksOnPage).append(",").append(booksOnPage);
            }
            
            System.out.print(sqlBuilder.toString());
            rs = stmt.executeQuery(sqlBuilder.toString());
            
            currentCargoList = new ArrayList<Cargo>();
            
            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(rs.getLong("id"));
                cargo.setCompanyId(rs.getInt("company_id"));
                cargo.setCtypeId(rs.getInt("ctype_id"));
                cargo.setDateYear(rs.getInt("date_year"));
                cargo.setDesc(rs.getString("desc"));
                // cargo.setShort_desc(rs.getString("short_des"));
                cargo.setName(rs.getString("name"));
                cargo.setTripCount(rs.getInt("trip_count"));
                cargo.setTripNumber(rs.getInt("trip_number"));
                
                cargo.setCars(rs.getString("cars"));
                cargo.setCompany(rs.getString("company"));
                cargo.setCargo_type(rs.getString("cargo_type"));
                
                currentCargoList.add(cargo);
                
                System.out.print(currentCargoList.toString());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //SEARCH QUERTY
    private void cargoSearchSQL(String sql) {
        
        // System.out.print(sql);
        StringBuilder sqlBuilder = new StringBuilder(sql);
        //-
        currentSearchSql = sql;
        //-
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            
            conn = Database.getConnection();
            stmt = conn.createStatement();

                //Pagination
                rs = stmt.executeQuery(sqlBuilder.toString());
                rs.last();

                totalSearchCount = rs.getRow();
                fillSearchPageNumbers(totalSearchCount, booksOnPage);      
                //
                
            if (totalSearchCount > booksOnPage) {
                 sqlBuilder.append(" limit ").append(selectedSearchPageNumber * booksOnPage - booksOnPage).append(",").append(booksOnPage);
            }

            rs = stmt.executeQuery(sqlBuilder.toString());
            
            currentCargoSearchList = new ArrayList<Cargo>();
            
            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(rs.getLong("id"));
                cargo.setCompanyId(rs.getInt("company_id"));
                cargo.setCtypeId(rs.getInt("ctype_id"));
                cargo.setDateYear(rs.getInt("date_year"));
                cargo.setDesc(rs.getString("desc"));
                cargo.setName(rs.getString("name"));
                cargo.setTripCount(rs.getInt("trip_count"));
                cargo.setTripNumber(rs.getInt("trip_number"));
                
                cargo.setCars(rs.getString("cars"));
                cargo.setCompany(rs.getString("company"));
                cargo.setCargo_type(rs.getString("cargo_type"));
                
                currentCargoSearchList.add(cargo);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

     private void SQL(String sql) {
        //System.out.print(sql);
   
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            currentCargoList = new ArrayList<Cargo>();
            
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setId(rs.getLong("id"));
                cargo.setCompanyId(rs.getInt("company_id"));
                cargo.setCtypeId(rs.getInt("ctype_id"));
                cargo.setDateYear(rs.getInt("date_year"));
                cargo.setDesc(rs.getString("desc"));
                cargo.setName(rs.getString("name"));
                cargo.setTripCount(rs.getInt("trip_count"));
                cargo.setTripNumber(rs.getInt("trip_number"));
                
                cargo.setCars(rs.getString("cars"));
                cargo.setCompany(rs.getString("company"));
                cargo.setCargo_type(rs.getString("cargo_type"));
                
                currentCargoList.add(cargo);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /* SQL QUERIES*/
    public void cargoAll() {

        pageSearchNumbers = new ArrayList<Integer>();     
        currentCargoSearchList = null; 
        searchLetter = '*';
        typeId = 0;
        
        cargoTableSQL(
            "select g.id, g.company_id, g.ctype_id, g.date_year, g.desc, g.name, g.trip_count, g.trip_number, g.image, "
            + " cs.name as cars,"
            + " ct.name as cargo_type,"
            + " cy.name as company"
            + " from cargo g "
                + " inner join cars cs on g.car_id = cs.id" 
                + " inner join company cy on g.company_id = cy.id" 
                + " inner join cargo_type ct on g.ctype_id = ct.id"
        );
    }
    
       
    public String letterSearch() {
        
        currentCargoList = null;  
        pageNumbers = new ArrayList<Integer>();
        selectedSearchPageNumber = 1;
        typeId = 0;
        totalCount = 0;
        
        Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        searchLetter = param.get("letter").charAt(0);
        
        cargoSearchSQL(
            "select g.id, g.company_id, g.ctype_id, g.date_year, g.desc, g.name, g.trip_count, g.trip_number, g.image, "
            + " cs.name as cars,"
            + " ct.name as cargo_type,"
            + " cy.name as company"
            + " from cargo g "
                + " inner join cars cs on g.car_id = cs.id" 
                + " inner join company cy on g.company_id = cy.id" 
                + " inner join cargo_type ct on g.ctype_id = ct.id"
            + " where substr(g.name,1,1)='" + searchLetter + "' order by g.name"
        );
        
        return "/pages/search_all?faces-redirect=true";
    }
    
    public String fillContentPage() {
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer cargo_id = Integer.valueOf(params.get("cargo_id"));
   
        SQL(
            "select g.id, g.company_id, g.ctype_id, g.date_year, g.desc, g.name, g.trip_count, g.trip_number, g.image, "
            + " cs.name as cars,"
            + " ct.name as cargo_type,"
            + " cy.name as company"
            + " from cargo g "
                + " inner join cars cs on g.car_id = cs.id" 
                + " inner join company cy on g.company_id = cy.id" 
                + " inner join cargo_type ct on g.ctype_id = ct.id"
            + " where g.id= " + cargo_id + ""
        );
        
        return "/pages/content?faces-redirect=true";
    }
    
    public String fillSearchContentPage() {

        currentCargoSearchList = null; 
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer cargo_id = Integer.valueOf(params.get("cargo_id"));
   
        SQL(
            "select g.id, g.company_id, g.ctype_id, g.date_year, g.desc, g.name, g.trip_count, g.trip_number, g.image, "
            + " cs.name as cars,"
            + " ct.name as cargo_type,"
            + " cy.name as company"
            + " from cargo g "
                + " inner join cars cs on g.car_id = cs.id" 
                + " inner join company cy on g.company_id = cy.id" 
                + " inner join cargo_type ct on g.ctype_id = ct.id"
            + " where g.id= " + cargo_id + ""
        );

        return "/pages/search_content?faces-redirect=true";
    }
    
     public String fillSearchByCar() {

        currentCargoList = null;  
        pageNumbers = new ArrayList<Integer>();
        selectedSearchPageNumber = 1;
        selectedPageNumber = 1;
        searchLetter = '*';
        typeId = 0;
        totalCount = 0;
         
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        typeId = Integer.valueOf(params.get("id"));
   
        cargoSearchSQL(
            "select g.id, g.company_id, g.ctype_id, g.date_year, g.desc, g.name, g.trip_count, g.trip_number, g.image, "
            + " cs.name as cars,"
            + " ct.name as cargo_type,"
            + " cy.name as company"
            + " from cargo g "
                + " inner join cars cs on g.car_id = cs.id" 
                + " inner join company cy on g.company_id = cy.id" 
                + " inner join cargo_type ct on g.ctype_id = ct.id"
            + " where g.car_id= " + typeId + ""
        );
        
        return "/pages/search_all?id=" + typeId + "&faces-redirect=true";
    }
     
     public String fillCargoBySearch() {

        currentCargoList = null;  
        pageNumbers = new ArrayList<Integer>();
        selectedSearchPageNumber = 1;
        searchLetter = '*';
        typeId = 0;
        totalCount = 0;

        if (searchString.trim().length() == 0) {
            cargoAll();
            return "/pages/maintemp?faces-redirect=true";
        }

        StringBuilder sql = new StringBuilder(
            "select g.id, g.company_id, g.ctype_id, g.date_year, g.desc, g.name, g.trip_count, g.trip_number, g.image, "
            + " cs.name as cars,"
            + " ct.name as cargo_type,"
            + " cy.name as company"
            + " from cargo g "
                + " inner join cars cs on g.car_id = cs.id" 
                + " inner join company cy on g.company_id = cy.id" 
                + " inner join cargo_type ct on g.ctype_id = ct.id ");

        if (searchType == SearchType.COMPANY) {
            sql.append("where lower(cy.name) like '%" + searchString.toLowerCase() + "%' order by g.id ");
        } else if (searchType == SearchType.CARS) {
            sql.append("where lower(cs.name) like '%" + searchString.toLowerCase() + "%'");
        } else if (searchType == SearchType.CARGO_TYPE) {
            sql.append("where lower(ct.name) like '%" + searchString.toLowerCase() + "%' order by g.id ");
        }
        
        cargoSearchSQL(sql.toString());
        
        return "/pages/search_all?faces-redirect=true";
    }
    
    public Character[] getLetters() {
        Character[] letters = new Character[25];
        letters[0] = 'A';
        letters[1] = 'W';
        letters[2] = 'E';
        letters[3] = 'R';
        letters[4] = 'T';
        letters[5] = 'Y';
        letters[6] = 'U';
        letters[7] = 'I';
        letters[8] = 'O';
        letters[9] = 'P';
        letters[10] = 'S';
        letters[11] = 'D';
        letters[12] = 'F';
        letters[13] = 'G';
        letters[14] = 'H';
        letters[15] = 'J';
        letters[16] = 'K';
        letters[17] = 'L';
        letters[18] = 'Z';
        letters[19] = 'X';
        letters[20] = 'C';
        letters[21] = 'V';
        letters[22] = 'B';
        letters[23] = 'N';
        letters[24] = 'M';

        return letters;
    }
    
    private void fillPageNumbers(long totalBooksCount, int booksCountOnPage) {

        int pageCount = 0;

        if (totalBooksCount % booksCountOnPage == 0) {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) : 0;
        } else {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) + 1 : 0;
        }

        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
    }

    private void fillSearchPageNumbers(long totalBooksCount, int booksCountOnPage) {

        int pageCount = 0;

        if (totalBooksCount % booksCountOnPage == 0) {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) : 0;
        } else {
            pageCount = booksCountOnPage > 0 ? (int) (totalBooksCount / booksCountOnPage) + 1 : 0;
        }

        pageSearchNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageSearchNumbers.add(i);
        }
    }

    public void selectPage() {
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedPageNumber = Integer.valueOf(params.get("page_number"));
        requestFromPager = true;
        cargoTableSQL(currentSql);
    }

    public void selectSearchPage() {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedSearchPageNumber = Integer.valueOf(params.get("page_number"));
        requestFromSearchPager = true;
        cargoSearchSQL(currentSearchSql);
    }
        
        
    public int totalRowCount() {
        if(totalCount != 0){
            return totalCount;
        } if(totalSearchCount != 0){
            return totalSearchCount;
        }

        return 0;
    }
        
    public void searchStringChanged(ValueChangeEvent e) {
    searchString = e.getNewValue().toString();
    }

    public void searchTypeChanged(ValueChangeEvent e) {
        searchType = (SearchType) e.getNewValue();
    }

    public ArrayList<Integer> getPageSearchNumbers() {
        return pageSearchNumbers;
    }

    public void setPageSearchNumbers(ArrayList<Integer> pageSearchNumbers) {
        this.pageSearchNumbers = pageSearchNumbers;
    }
    
    public ArrayList<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    public ArrayList<Cargo> getCurrentCargoList() {

        return currentCargoList;
    }
    
    public ArrayList<Cargo> getCurrentCargoSearchList() {

        return currentCargoSearchList;
    }
    
    public SearchType getSearchType() {
        return searchType;
    }
    
    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
    
    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
    
    //02.01
    public char getSearchLetter() {
        return searchLetter;
    }
    public void setSearchLetter(char searchLetter) {
        this.searchLetter = searchLetter;
    }

    public Integer getTypeId() {
        return typeId;
    }
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public long getSelectedSearchPageNumber() {
        return selectedSearchPageNumber;
    }
    public void setSelectedSearchPageNumber(long selectedSearchPageNumber) {
        this.selectedSearchPageNumber = selectedSearchPageNumber;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }
    public void setSelectedPageNumber(long selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalSearchCount() {
        return totalSearchCount;
    }

    public void setTotalSearchCount(int totalSearchCount) {
        this.totalSearchCount = totalSearchCount;
    }
    
    public byte[] getImage(int id) {
        
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        
        byte[] image = null;    
        
        try {
            
            conn = Database.getConnection();
            stmt = conn.createStatement();
            
            rs = stmt.executeQuery("select image from cargo where id=" + id);
            
            while (rs.next()) {
                image = rs.getBytes("image");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Cargo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Cargo.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            return image;  
        }
    }

    public byte[] getContent(int id) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;


        byte[] content = null;
        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("select content from cargo where id=" + id);
            while (rs.next()) {
                content = rs.getBytes("content");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Cargo.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Cargo.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        return content;
    }
}
