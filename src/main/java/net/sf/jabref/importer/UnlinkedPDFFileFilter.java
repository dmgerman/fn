begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|io
operator|.
name|FileFilter
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * {@link FileFilter} implementation, that allows only files which are not  * linked in any of the {@link BibEntry}s of the specified  * {@link BibDatabase}.<br>  *<br>  * This {@link FileFilter} sits on top of another {@link FileFilter}  * -implementation, which it first consults. Only if this major filefilter  * has accepted a file, this implementation will verify on that file.  *   * @author Nosh&Dan  * @version 12.11.2008 | 02:00:15  *   */
end_comment

begin_class
DECL|class|UnlinkedPDFFileFilter
specifier|public
class|class
name|UnlinkedPDFFileFilter
implements|implements
name|FileFilter
block|{
DECL|field|lookup
specifier|private
specifier|final
name|DatabaseFileLookup
name|lookup
decl_stmt|;
DECL|field|fileFilter
specifier|private
specifier|final
name|FileFilter
name|fileFilter
decl_stmt|;
DECL|method|UnlinkedPDFFileFilter (FileFilter fileFilter, BibDatabase database)
specifier|public
name|UnlinkedPDFFileFilter
parameter_list|(
name|FileFilter
name|fileFilter
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|fileFilter
operator|=
name|fileFilter
expr_stmt|;
name|this
operator|.
name|lookup
operator|=
operator|new
name|DatabaseFileLookup
argument_list|(
name|database
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|accept (File pathname)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|pathname
parameter_list|)
block|{
return|return
name|fileFilter
operator|.
name|accept
argument_list|(
name|pathname
argument_list|)
operator|&&
operator|!
name|lookup
operator|.
name|lookupDatabase
argument_list|(
name|pathname
argument_list|)
return|;
block|}
block|}
end_class

end_unit

