begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|export
operator|.
name|layout
operator|.
name|*
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
name|Util
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
name|Globals
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

begin_comment
comment|/**  * Will expand the relative PDF path.  *   * TODO Search also relative to Bib-file.  *   * @author $Author$  * @version $Revision$ ($Date$)  */
end_comment

begin_class
DECL|class|ResolvePDF
specifier|public
class|class
name|ResolvePDF
implements|implements
name|LayoutFormatter
block|{
DECL|method|format (String field)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|String
name|dir
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
decl_stmt|;
name|File
name|f
init|=
name|Util
operator|.
name|expandFilename
argument_list|(
name|field
argument_list|,
operator|new
name|String
index|[]
block|{
name|dir
block|,
literal|"."
block|}
argument_list|)
decl_stmt|;
comment|/* 		 * Stumbled over this while investigating  		 *  		 * https://sourceforge.net/tracker/index.php?func=detail&aid=1469903&group_id=92314&atid=600306  		 */
if|if
condition|(
name|Globals
operator|.
name|ON_WIN
operator|&&
name|f
operator|!=
literal|null
condition|)
block|{
name|field
operator|=
literal|"file://"
operator|+
name|f
operator|.
name|getPath
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"/"
argument_list|)
expr_stmt|;
block|}
return|return
name|field
return|;
block|}
block|}
end_class

end_unit

