begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLConnection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|PropertyResourceBundle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ResourceBundle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ResourceBundle
operator|.
name|Control
import|;
end_import

begin_comment
comment|/**  * {@link Control} class allowing properties bundles to be in different encodings.  *  * @see<a href="http://stackoverflow.com/questions/4659929/how-to-use-utf-8-in-resource-properties-with-resourcebundle">utf-8  *      and property files</a>  */
end_comment

begin_class
DECL|class|EncodingControl
specifier|public
class|class
name|EncodingControl
extends|extends
name|Control
block|{
DECL|field|encoding
specifier|private
specifier|final
name|Charset
name|encoding
decl_stmt|;
DECL|method|EncodingControl (Charset encoding)
specifier|public
name|EncodingControl
parameter_list|(
name|Charset
name|encoding
parameter_list|)
block|{
name|this
operator|.
name|encoding
operator|=
name|encoding
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|newBundle (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
specifier|public
name|ResourceBundle
name|newBundle
parameter_list|(
name|String
name|baseName
parameter_list|,
name|Locale
name|locale
parameter_list|,
name|String
name|format
parameter_list|,
name|ClassLoader
name|loader
parameter_list|,
name|boolean
name|reload
parameter_list|)
throws|throws
name|IllegalAccessException
throws|,
name|InstantiationException
throws|,
name|IOException
block|{
comment|// The below is a copy of the default implementation.
name|String
name|bundleName
init|=
name|toBundleName
argument_list|(
name|baseName
argument_list|,
name|locale
argument_list|)
decl_stmt|;
name|String
name|resourceName
init|=
name|toResourceName
argument_list|(
name|bundleName
argument_list|,
literal|"properties"
argument_list|)
decl_stmt|;
name|ResourceBundle
name|bundle
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|reload
condition|)
block|{
name|URL
name|url
init|=
name|loader
operator|.
name|getResource
argument_list|(
name|resourceName
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|!=
literal|null
condition|)
block|{
name|URLConnection
name|connection
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
if|if
condition|(
name|connection
operator|!=
literal|null
condition|)
block|{
name|connection
operator|.
name|setUseCaches
argument_list|(
literal|false
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|stream
init|=
name|connection
operator|.
name|getInputStream
argument_list|()
init|)
block|{
name|bundle
operator|=
operator|new
name|PropertyResourceBundle
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|,
name|encoding
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
else|else
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|loader
operator|.
name|getResourceAsStream
argument_list|(
name|resourceName
argument_list|)
init|)
block|{
name|bundle
operator|=
operator|new
name|PropertyResourceBundle
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|,
name|encoding
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|bundle
return|;
block|}
block|}
end_class

end_unit
