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
name|Optional
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
name|BibDatabaseMode
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
name|BibDatabaseModeDetection
import|;
end_import

begin_comment
comment|/**  * Represents everything related to a .bib file.  *<p>  * The entries are stored in BibDatabase, the other data in MetaData and the options relevant for this file in Defaults.  */
end_comment

begin_class
DECL|class|BibDatabaseContext
specifier|public
class|class
name|BibDatabaseContext
block|{
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
DECL|field|defaults
specifier|private
specifier|final
name|Defaults
name|defaults
decl_stmt|;
DECL|method|BibDatabaseContext ()
specifier|public
name|BibDatabaseContext
parameter_list|()
block|{
name|this
argument_list|(
operator|new
name|Defaults
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData, Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
operator|.
name|defaults
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|defaults
argument_list|)
expr_stmt|;
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
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData, File file, Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|,
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|defaults
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
DECL|method|getMode ()
specifier|public
name|BibDatabaseMode
name|getMode
parameter_list|()
block|{
name|Optional
argument_list|<
name|BibDatabaseMode
argument_list|>
name|mode
init|=
name|metaData
operator|.
name|getMode
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|mode
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|BibDatabaseMode
name|inferredMode
init|=
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|defaults
operator|.
name|mode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
operator|)
operator|||
operator|(
name|inferredMode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
operator|)
condition|)
block|{
return|return
name|BibDatabaseMode
operator|.
name|BIBLATEX
return|;
block|}
else|else
block|{
return|return
name|BibDatabaseMode
operator|.
name|BIBTEX
return|;
block|}
block|}
return|return
name|mode
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setMode (BibDatabaseMode bibDatabaseMode)
specifier|public
name|void
name|setMode
parameter_list|(
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
block|{
name|metaData
operator|.
name|setMode
argument_list|(
name|bibDatabaseMode
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
DECL|method|isBiblatexMode ()
specifier|public
name|boolean
name|isBiblatexMode
parameter_list|()
block|{
return|return
name|getMode
argument_list|()
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
return|;
block|}
block|}
end_class

end_unit

