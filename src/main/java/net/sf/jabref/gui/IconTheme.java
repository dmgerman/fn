begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

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
name|MalformedURLException
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
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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

begin_class
DECL|class|IconTheme
specifier|public
class|class
name|IconTheme
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|IconTheme
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|KEY_TO_ICON
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|KEY_TO_ICON
init|=
name|readIconThemeFile
argument_list|(
name|IconTheme
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/images/crystal_16/Icons.properties"
argument_list|)
argument_list|,
literal|"/images/crystal_16/"
argument_list|)
decl_stmt|;
DECL|field|DEFAULT_ICON_PATH
specifier|private
specifier|static
specifier|final
name|String
name|DEFAULT_ICON_PATH
init|=
literal|"/images/crystal_16/red.png"
decl_stmt|;
comment|/**      * Looks up the URL for the image representing the given function, in the resource      * file listing images.      *      * @param name The name of the icon, such as "open", "save", "saveAs" etc.      * @return The URL to the actual image to use.      */
DECL|method|getIconUrl (String name)
specifier|public
specifier|static
name|URL
name|getIconUrl
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
name|path
init|=
name|KEY_TO_ICON
operator|.
name|getOrDefault
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|name
argument_list|,
literal|"icon name"
argument_list|)
argument_list|,
name|DEFAULT_ICON_PATH
argument_list|)
decl_stmt|;
return|return
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|IconTheme
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|path
argument_list|)
argument_list|,
literal|"url"
argument_list|)
return|;
block|}
comment|/**      * Constructs an ImageIcon for the image representing the given function, in the resource      * file listing images.      *      * @param name The name of the icon, such as "open", "save", "saveAs" etc.      * @return The ImageIcon for the function.      */
DECL|method|getImage (String name)
specifier|public
specifier|static
name|ImageIcon
name|getImage
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|new
name|ImageIcon
argument_list|(
name|getIconUrl
argument_list|(
name|name
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Get a Map of all application icons mapped from their keys.      *      * @return A Map containing all icons used in the application.      */
DECL|method|getAllIcons ()
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getAllIcons
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|KEY_TO_ICON
argument_list|)
return|;
block|}
comment|/**      * Read a typical java property url into a Map. Currently doesn't support escaping      * of the '=' character - it simply looks for the first '=' to determine where the key ends.      * Both the key and the value is trimmed for whitespace at the ends.      *      * @param url   The URL to read information from.      * @param prefix A String to prefix to all values read. Can represent e.g. the directory      *               where icon files are to be found.      * @return A Map containing all key-value pairs found.      */
DECL|method|readIconThemeFile (URL url, String prefix)
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|readIconThemeFile
parameter_list|(
name|URL
name|url
parameter_list|,
name|String
name|prefix
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|url
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|prefix
argument_list|,
literal|"prefix"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|url
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
name|String
name|line
decl_stmt|;
while|while
condition|(
operator|(
name|line
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|line
operator|.
name|contains
argument_list|(
literal|"="
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|int
name|index
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|"="
argument_list|)
decl_stmt|;
name|String
name|key
init|=
name|line
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|String
name|value
init|=
name|prefix
operator|+
name|line
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
decl_stmt|;
name|result
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to read default icon theme."
argument_list|)
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

