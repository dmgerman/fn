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
comment|//Util.pr(""+field);
name|File
name|f
init|=
name|Util
operator|.
name|expandFilename
argument_list|(
name|field
argument_list|,
name|dir
argument_list|)
decl_stmt|;
return|return
operator|(
name|f
operator|!=
literal|null
condition|?
literal|"file://"
operator|+
name|f
operator|.
name|getPath
argument_list|()
else|:
name|field
operator|)
return|;
block|}
block|}
end_class

end_unit

