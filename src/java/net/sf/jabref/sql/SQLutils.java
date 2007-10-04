begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * SQLutils.java  *  * Created on October 4, 2007, 5:28 PM  *  * To change this template, choose Tools | Template Manager  * and open the template in the editor.  */
end_comment

begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  *  * @author pattonlk  */
end_comment

begin_class
DECL|class|SQLutils
specifier|public
class|class
name|SQLutils
block|{
DECL|method|connect_mysql (String url, String username, String password)
specifier|public
specifier|static
name|Connection
name|connect_mysql
parameter_list|(
name|String
name|url
parameter_list|,
name|String
name|username
parameter_list|,
name|String
name|password
parameter_list|)
throws|throws
name|Exception
block|{
comment|/**      * This routine accepts the location of a MySQL database specified as a url as       * well as the username and password for the MySQL user with appropriate access      * to this database.  The routine returns a valid Connection object if the MySQL       * database is successfully opened. It returns a null object otherwise.      */
name|Class
operator|.
name|forName
argument_list|(
literal|"com.mysql.jdbc.Driver"
argument_list|)
operator|.
name|newInstance
argument_list|()
expr_stmt|;
name|Connection
name|conn
init|=
name|DriverManager
operator|.
name|getConnection
argument_list|(
name|url
argument_list|,
name|username
argument_list|,
name|password
argument_list|)
decl_stmt|;
return|return
name|conn
return|;
block|}
block|}
end_class

end_unit

