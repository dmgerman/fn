begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.shared
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|DatabaseTests
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
import|;
end_import

begin_class
annotation|@
name|Category
argument_list|(
name|DatabaseTests
operator|.
name|class
argument_list|)
DECL|class|DBMSTypeTest
specifier|public
class|class
name|DBMSTypeTest
block|{
annotation|@
name|Test
DECL|method|testToString ()
specifier|public
name|void
name|testToString
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"MySQL"
argument_list|,
name|DBMSType
operator|.
name|MYSQL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Oracle"
argument_list|,
name|DBMSType
operator|.
name|ORACLE
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"PostgreSQL"
argument_list|,
name|DBMSType
operator|.
name|POSTGRESQL
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDriverClassPath ()
specifier|public
name|void
name|testGetDriverClassPath
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"com.mysql.jdbc.Driver"
argument_list|,
name|DBMSType
operator|.
name|MYSQL
operator|.
name|getDriverClassPath
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"oracle.jdbc.driver.OracleDriver"
argument_list|,
name|DBMSType
operator|.
name|ORACLE
operator|.
name|getDriverClassPath
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"com.impossibl.postgres.jdbc.PGDriver"
argument_list|,
name|DBMSType
operator|.
name|POSTGRESQL
operator|.
name|getDriverClassPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFromString ()
specifier|public
name|void
name|testFromString
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|DBMSType
operator|.
name|MYSQL
argument_list|,
name|DBMSType
operator|.
name|fromString
argument_list|(
literal|"MySQL"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|DBMSType
operator|.
name|ORACLE
argument_list|,
name|DBMSType
operator|.
name|fromString
argument_list|(
literal|"Oracle"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|DBMSType
operator|.
name|POSTGRESQL
argument_list|,
name|DBMSType
operator|.
name|fromString
argument_list|(
literal|"PostgreSQL"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|DBMSType
operator|.
name|fromString
argument_list|(
literal|"XXX"
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetUrl ()
specifier|public
name|void
name|testGetUrl
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"jdbc:mysql://localhost:3306/xe"
argument_list|,
name|DBMSType
operator|.
name|MYSQL
operator|.
name|getUrl
argument_list|(
literal|"localhost"
argument_list|,
literal|3306
argument_list|,
literal|"xe"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"jdbc:oracle:thin:@localhost:1521:xe"
argument_list|,
name|DBMSType
operator|.
name|ORACLE
operator|.
name|getUrl
argument_list|(
literal|"localhost"
argument_list|,
literal|1521
argument_list|,
literal|"xe"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"jdbc:pgsql://localhost:5432/xe"
argument_list|,
name|DBMSType
operator|.
name|POSTGRESQL
operator|.
name|getUrl
argument_list|(
literal|"localhost"
argument_list|,
literal|5432
argument_list|,
literal|"xe"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDefaultPort ()
specifier|public
name|void
name|testGetDefaultPort
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|3306
argument_list|,
name|DBMSType
operator|.
name|MYSQL
operator|.
name|getDefaultPort
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5432
argument_list|,
name|DBMSType
operator|.
name|POSTGRESQL
operator|.
name|getDefaultPort
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1521
argument_list|,
name|DBMSType
operator|.
name|ORACLE
operator|.
name|getDefaultPort
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

