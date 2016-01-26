begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseType
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_class
DECL|class|LoadedDatabase
specifier|public
class|class
name|LoadedDatabase
block|{
DECL|field|DATABASE_TYPE
specifier|private
specifier|static
specifier|final
name|String
name|DATABASE_TYPE
init|=
literal|"DATABASE_TYPE"
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
name|database
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|method|LoadedDatabase ()
specifier|public
name|LoadedDatabase
parameter_list|()
block|{
name|this
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|LoadedDatabase (BibDatabase database)
specifier|public
name|LoadedDatabase
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|LoadedDatabase (BibDatabase database, MetaData metaData)
specifier|public
name|LoadedDatabase
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
DECL|method|LoadedDatabase (BibDatabase database, MetaData metaData, File file)
specifier|public
name|LoadedDatabase
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
DECL|method|getType ()
specifier|public
name|BibDatabaseType
name|getType
parameter_list|()
block|{
return|return
name|BibDatabaseType
operator|.
name|valueOf
argument_list|(
name|metaData
operator|.
name|getData
argument_list|(
name|DATABASE_TYPE
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
DECL|method|setType (BibDatabaseType type)
specifier|public
name|void
name|setType
parameter_list|(
name|BibDatabaseType
name|type
parameter_list|)
block|{
name|Vector
argument_list|<
name|String
argument_list|>
name|list
init|=
operator|new
name|Vector
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
name|type
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|putData
argument_list|(
name|DATABASE_TYPE
argument_list|,
name|list
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the file where this database was last saved to or loaded from, if any.      *      * @return The relevant File, or null if none is defined.      */
DECL|method|getDatabaseFile ()
specifier|public
name|File
name|getDatabaseFile
parameter_list|()
block|{
return|return
name|metaData
operator|.
name|getFile
argument_list|()
return|;
block|}
DECL|method|getDatabase ()
specifier|public
name|BibDatabase
name|getDatabase
parameter_list|()
block|{
return|return
name|database
return|;
block|}
DECL|method|getMetaData ()
specifier|public
name|MetaData
name|getMetaData
parameter_list|()
block|{
return|return
name|metaData
return|;
block|}
block|}
end_class

end_unit

