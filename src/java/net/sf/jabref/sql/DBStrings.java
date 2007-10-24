begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * DBStrings.java  *  * Created on October 1, 2007, 6:33 PM  *  * To change this template, choose Tools | Template Manager  * and open the template in the editor.  */
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_comment
comment|/**  *  * @author pattonlk  */
end_comment

begin_class
DECL|class|DBStrings
specifier|public
class|class
name|DBStrings
block|{
DECL|field|serverType
specifier|private
name|String
name|serverType
decl_stmt|;
DECL|field|serverHostname
specifier|private
name|String
name|serverHostname
decl_stmt|;
DECL|field|database
specifier|private
name|String
name|database
decl_stmt|;
DECL|field|username
specifier|private
name|String
name|username
decl_stmt|;
DECL|field|password
specifier|private
name|String
name|password
decl_stmt|;
DECL|field|serverTypes
specifier|private
name|String
index|[]
name|serverTypes
decl_stmt|;
DECL|field|isInitialized
specifier|private
name|boolean
name|isInitialized
decl_stmt|;
DECL|field|configValid
specifier|private
name|boolean
name|configValid
decl_stmt|;
comment|/** Creates a new instance of DBStrings */
DECL|method|DBStrings ()
specifier|public
name|DBStrings
parameter_list|()
block|{
name|this
operator|.
name|setServerType
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|setServerHostname
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|setDatabase
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|setUsername
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|setPassword
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|isInitialized
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|isConfigValid
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
comment|//String [] servers = {Globals.lang("MySQL"), Globals.lang("Derby")};
name|String
index|[]
name|servers
init|=
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"MySQL"
argument_list|)
block|}
decl_stmt|;
name|setServerTypes
argument_list|(
name|servers
argument_list|)
expr_stmt|;
name|setServerType
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"MySQL"
argument_list|)
argument_list|)
expr_stmt|;
name|setServerHostname
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"localhost"
argument_list|)
argument_list|)
expr_stmt|;
name|setDatabase
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"jabref"
argument_list|)
argument_list|)
expr_stmt|;
name|setUsername
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"root"
argument_list|)
argument_list|)
expr_stmt|;
name|setPassword
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|isInitialized
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setServerType (String serverType)
specifier|public
name|void
name|setServerType
parameter_list|(
name|String
name|serverType
parameter_list|)
block|{
name|this
operator|.
name|serverType
operator|=
name|serverType
expr_stmt|;
block|}
DECL|method|setServerHostname (String serverHostname)
specifier|public
name|void
name|setServerHostname
parameter_list|(
name|String
name|serverHostname
parameter_list|)
block|{
name|this
operator|.
name|serverHostname
operator|=
name|serverHostname
expr_stmt|;
block|}
DECL|method|setDatabase (String database)
specifier|public
name|void
name|setDatabase
parameter_list|(
name|String
name|database
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
DECL|method|setUsername (String username)
specifier|public
name|void
name|setUsername
parameter_list|(
name|String
name|username
parameter_list|)
block|{
name|this
operator|.
name|username
operator|=
name|username
expr_stmt|;
block|}
DECL|method|setPassword (String password)
specifier|public
name|void
name|setPassword
parameter_list|(
name|String
name|password
parameter_list|)
block|{
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
block|}
DECL|method|getServerType ()
specifier|public
name|String
name|getServerType
parameter_list|()
block|{
return|return
name|serverType
return|;
block|}
DECL|method|getServerHostname ()
specifier|public
name|String
name|getServerHostname
parameter_list|()
block|{
return|return
name|serverHostname
return|;
block|}
DECL|method|getDatabase ()
specifier|public
name|String
name|getDatabase
parameter_list|()
block|{
return|return
name|database
return|;
block|}
DECL|method|getUsername ()
specifier|public
name|String
name|getUsername
parameter_list|()
block|{
return|return
name|username
return|;
block|}
DECL|method|getPassword ()
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|password
return|;
block|}
DECL|method|getServerTypes ()
specifier|public
name|String
index|[]
name|getServerTypes
parameter_list|()
block|{
return|return
name|serverTypes
return|;
block|}
DECL|method|setServerTypes (String[] serverTypes)
specifier|public
name|void
name|setServerTypes
parameter_list|(
name|String
index|[]
name|serverTypes
parameter_list|)
block|{
name|this
operator|.
name|serverTypes
operator|=
name|serverTypes
expr_stmt|;
block|}
DECL|method|isInitialized ()
specifier|public
name|boolean
name|isInitialized
parameter_list|()
block|{
return|return
name|isInitialized
return|;
block|}
DECL|method|isInitialized (boolean isInitialized)
specifier|public
name|void
name|isInitialized
parameter_list|(
name|boolean
name|isInitialized
parameter_list|)
block|{
name|this
operator|.
name|isInitialized
operator|=
name|isInitialized
expr_stmt|;
block|}
DECL|method|isConfigValid ()
specifier|public
name|boolean
name|isConfigValid
parameter_list|()
block|{
return|return
name|configValid
return|;
block|}
DECL|method|isConfigValid (boolean configValid)
specifier|public
name|void
name|isConfigValid
parameter_list|(
name|boolean
name|configValid
parameter_list|)
block|{
name|this
operator|.
name|configValid
operator|=
name|configValid
expr_stmt|;
block|}
block|}
end_class

end_unit

