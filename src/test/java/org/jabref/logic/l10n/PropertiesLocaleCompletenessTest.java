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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|ResourceBundle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Splitter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_comment
comment|/**  * Checks that all property files are correctly encoded and can be loaded without errors.  */
end_comment

begin_class
DECL|class|PropertiesLocaleCompletenessTest
specifier|public
class|class
name|PropertiesLocaleCompletenessTest
block|{
annotation|@
name|Test
DECL|method|testi10nFiles ()
specifier|public
name|void
name|testi10nFiles
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|pathStream
init|=
name|Files
operator|.
name|list
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src/main/resources/l10n"
argument_list|)
argument_list|)
init|)
block|{
for|for
control|(
name|Path
name|p
range|:
name|pathStream
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
control|)
block|{
name|String
index|[]
name|parts
init|=
name|getParts
argument_list|(
name|p
argument_list|)
decl_stmt|;
name|String
name|prefix
init|=
literal|"l10n/"
operator|+
name|parts
index|[
literal|0
index|]
decl_stmt|;
name|Locale
name|locale
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|==
literal|3
condition|)
block|{
name|locale
operator|=
operator|new
name|Locale
argument_list|(
name|parts
index|[
literal|1
index|]
argument_list|,
name|parts
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|locale
operator|=
operator|new
name|Locale
argument_list|(
name|parts
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
name|checkPropertiesFile
argument_list|(
name|locale
argument_list|,
name|prefix
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getParts (Path p)
specifier|private
name|String
index|[]
name|getParts
parameter_list|(
name|Path
name|p
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|elements
init|=
name|Splitter
operator|.
name|on
argument_list|(
literal|"_"
argument_list|)
operator|.
name|splitToList
argument_list|(
name|p
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"\\."
argument_list|)
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|String
index|[]
name|parts
init|=
operator|new
name|String
index|[
name|elements
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|elements
operator|.
name|toArray
argument_list|(
name|parts
argument_list|)
expr_stmt|;
return|return
name|parts
return|;
block|}
annotation|@
name|Test
DECL|method|testCompletenessOfBundles ()
specifier|public
name|void
name|testCompletenessOfBundles
parameter_list|()
block|{
for|for
control|(
name|String
name|lang
range|:
name|Languages
operator|.
name|LANGUAGES
operator|.
name|values
argument_list|()
control|)
block|{
name|Path
name|menuPropertyFile
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/main/resources"
argument_list|)
operator|.
name|resolve
argument_list|(
name|Localization
operator|.
name|MENU_RESOURCE_PREFIX
operator|+
literal|"_"
operator|+
name|lang
operator|+
literal|".properties"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|menuPropertyFile
argument_list|)
argument_list|)
expr_stmt|;
name|Path
name|messagePropertyFile
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/main/resources"
argument_list|)
operator|.
name|resolve
argument_list|(
name|Localization
operator|.
name|RESOURCE_PREFIX
operator|+
literal|"_"
operator|+
name|lang
operator|+
literal|".properties"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|messagePropertyFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|checkPropertiesFile (Locale locale, String prefix)
specifier|private
name|void
name|checkPropertiesFile
parameter_list|(
name|Locale
name|locale
parameter_list|,
name|String
name|prefix
parameter_list|)
block|{
name|Locale
name|oldLocale
init|=
name|Locale
operator|.
name|getDefault
argument_list|()
decl_stmt|;
try|try
block|{
name|Locale
operator|.
name|setDefault
argument_list|(
name|locale
argument_list|)
expr_stmt|;
name|ResourceBundle
operator|.
name|getBundle
argument_list|(
name|prefix
argument_list|,
name|locale
argument_list|,
operator|new
name|EncodingControl
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
name|Locale
operator|.
name|setDefault
argument_list|(
name|oldLocale
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

