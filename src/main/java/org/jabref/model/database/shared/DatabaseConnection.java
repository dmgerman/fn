begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|Connection
import|;
end_import

begin_interface
DECL|interface|DatabaseConnection
specifier|public
interface|interface
name|DatabaseConnection
block|{
DECL|method|getProperties ()
name|DatabaseConnectionProperties
name|getProperties
parameter_list|()
function_decl|;
DECL|method|getConnection ()
name|Connection
name|getConnection
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

