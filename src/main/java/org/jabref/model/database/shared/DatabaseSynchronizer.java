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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_interface
DECL|interface|DatabaseSynchronizer
specifier|public
interface|interface
name|DatabaseSynchronizer
block|{
DECL|method|getDBName ()
name|String
name|getDBName
parameter_list|()
function_decl|;
DECL|method|pullChanges ()
name|void
name|pullChanges
parameter_list|()
function_decl|;
DECL|method|closeSharedDatabase ()
name|void
name|closeSharedDatabase
parameter_list|()
function_decl|;
DECL|method|registerListener (Object listener)
name|void
name|registerListener
parameter_list|(
name|Object
name|listener
parameter_list|)
function_decl|;
DECL|method|openSharedDatabase (DatabaseConnection connection)
name|void
name|openSharedDatabase
parameter_list|(
name|DatabaseConnection
name|connection
parameter_list|)
throws|throws
name|DatabaseNotSupportedException
function_decl|;
DECL|method|synchronizeSharedEntry (BibEntry bibEntry)
name|void
name|synchronizeSharedEntry
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
function_decl|;
DECL|method|synchronizeLocalDatabase ()
name|void
name|synchronizeLocalDatabase
parameter_list|()
function_decl|;
DECL|method|getConnectionProperties ()
name|DatabaseConnectionProperties
name|getConnectionProperties
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

