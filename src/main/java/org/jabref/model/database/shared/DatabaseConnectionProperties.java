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

begin_interface
DECL|interface|DatabaseConnectionProperties
specifier|public
interface|interface
name|DatabaseConnectionProperties
block|{
DECL|method|getType ()
name|DBMSType
name|getType
parameter_list|()
function_decl|;
DECL|method|getDatabase ()
name|String
name|getDatabase
parameter_list|()
function_decl|;
DECL|method|getPort ()
name|int
name|getPort
parameter_list|()
function_decl|;
DECL|method|getHost ()
name|String
name|getHost
parameter_list|()
function_decl|;
DECL|method|getUser ()
name|String
name|getUser
parameter_list|()
function_decl|;
DECL|method|getPassword ()
name|String
name|getPassword
parameter_list|()
function_decl|;
DECL|method|isValid ()
name|boolean
name|isValid
parameter_list|()
function_decl|;
DECL|method|getKeyStore ()
name|String
name|getKeyStore
parameter_list|()
function_decl|;
DECL|method|isUseSSL ()
name|boolean
name|isUseSSL
parameter_list|()
function_decl|;
DECL|method|getServerTimezone ()
name|String
name|getServerTimezone
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

