begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.protectedterms
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|protectedterms
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
name|FileNotFoundException
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
name|ArrayList
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
name|HashSet
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
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

begin_class
DECL|class|ProtectedTermsLoader
specifier|public
class|class
name|ProtectedTermsLoader
block|{
DECL|field|internalLists
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|internalLists
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
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
name|ProtectedTermsLoader
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|mainList
specifier|private
specifier|final
name|List
argument_list|<
name|ProtectedTermsList
argument_list|>
name|mainList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
name|internalLists
operator|.
name|put
argument_list|(
literal|"/protectedterms/months_weekdays.terms"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Months and weekdays in English"
argument_list|)
argument_list|)
expr_stmt|;
name|internalLists
operator|.
name|put
argument_list|(
literal|"/protectedterms/countries_territories.terms"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Countries and territories in English"
argument_list|)
argument_list|)
expr_stmt|;
name|internalLists
operator|.
name|put
argument_list|(
literal|"/protectedterms/electrical_engineering.terms"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Electrical engineering terms"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|ProtectedTermsLoader (ProtectedTermsPreferences preferences)
specifier|public
name|ProtectedTermsLoader
parameter_list|(
name|ProtectedTermsPreferences
name|preferences
parameter_list|)
block|{
name|update
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
DECL|method|getInternalLists ()
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getInternalLists
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|internalLists
operator|.
name|keySet
argument_list|()
argument_list|)
return|;
block|}
DECL|method|update (ProtectedTermsPreferences preferences)
specifier|public
name|void
name|update
parameter_list|(
name|ProtectedTermsPreferences
name|preferences
parameter_list|)
block|{
name|mainList
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Read internal lists
for|for
control|(
name|String
name|filename
range|:
name|preferences
operator|.
name|getEnabledInternalTermLists
argument_list|()
control|)
block|{
if|if
condition|(
name|internalLists
operator|.
name|containsKey
argument_list|(
name|filename
argument_list|)
condition|)
block|{
name|mainList
operator|.
name|add
argument_list|(
name|readProtectedTermsListFromResource
argument_list|(
name|filename
argument_list|,
name|internalLists
operator|.
name|get
argument_list|(
name|filename
argument_list|)
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Protected terms resource '"
operator|+
name|filename
operator|+
literal|"' is no longer available."
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|filename
range|:
name|preferences
operator|.
name|getDisabledInternalTermLists
argument_list|()
control|)
block|{
if|if
condition|(
name|internalLists
operator|.
name|containsKey
argument_list|(
name|filename
argument_list|)
condition|)
block|{
if|if
condition|(
operator|!
name|preferences
operator|.
name|getEnabledInternalTermLists
argument_list|()
operator|.
name|contains
argument_list|(
name|filename
argument_list|)
condition|)
block|{
name|mainList
operator|.
name|add
argument_list|(
name|readProtectedTermsListFromResource
argument_list|(
name|filename
argument_list|,
name|internalLists
operator|.
name|get
argument_list|(
name|filename
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Protected terms resource '"
operator|+
name|filename
operator|+
literal|"' is no longer available."
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Check if any new internal lists have emerged
for|for
control|(
name|String
name|filename
range|:
name|internalLists
operator|.
name|keySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|preferences
operator|.
name|getEnabledInternalTermLists
argument_list|()
operator|.
name|contains
argument_list|(
name|filename
argument_list|)
operator|&&
operator|!
name|preferences
operator|.
name|getDisabledInternalTermLists
argument_list|()
operator|.
name|contains
argument_list|(
name|filename
argument_list|)
condition|)
block|{
comment|// New internal list, add it
name|mainList
operator|.
name|add
argument_list|(
name|readProtectedTermsListFromResource
argument_list|(
name|filename
argument_list|,
name|internalLists
operator|.
name|get
argument_list|(
name|filename
argument_list|)
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"New protected terms resource '"
operator|+
name|filename
operator|+
literal|"' is available and enabled by default."
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Read external lists
for|for
control|(
name|String
name|filename
range|:
name|preferences
operator|.
name|getEnabledExternalTermLists
argument_list|()
control|)
block|{
try|try
block|{
name|mainList
operator|.
name|add
argument_list|(
name|readProtectedTermsListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found...
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot find protected terms file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|filename
range|:
name|preferences
operator|.
name|getDisabledExternalTermLists
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|preferences
operator|.
name|getEnabledExternalTermLists
argument_list|()
operator|.
name|contains
argument_list|(
name|filename
argument_list|)
condition|)
block|{
try|try
block|{
name|mainList
operator|.
name|add
argument_list|(
name|readProtectedTermsListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|filename
argument_list|)
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found...
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot find protected terms file "
operator|+
name|filename
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|reloadProtectedTermsList (ProtectedTermsList list)
specifier|public
name|void
name|reloadProtectedTermsList
parameter_list|(
name|ProtectedTermsList
name|list
parameter_list|)
block|{
try|try
block|{
name|ProtectedTermsList
name|newList
init|=
name|readProtectedTermsListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|list
operator|.
name|getLocation
argument_list|()
argument_list|)
argument_list|,
name|list
operator|.
name|isEnabled
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|index
init|=
name|mainList
operator|.
name|indexOf
argument_list|(
name|list
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|mainList
operator|.
name|set
argument_list|(
name|index
argument_list|,
name|newList
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem reloading protected terms file"
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
literal|"Problem with protected terms file '"
operator|+
name|list
operator|.
name|getLocation
argument_list|()
operator|+
literal|"'"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getProtectedTermsLists ()
specifier|public
name|List
argument_list|<
name|ProtectedTermsList
argument_list|>
name|getProtectedTermsLists
parameter_list|()
block|{
return|return
name|mainList
return|;
block|}
DECL|method|getProtectedTerms ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getProtectedTerms
parameter_list|()
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|ProtectedTermsList
name|list
range|:
name|mainList
control|)
block|{
if|if
condition|(
name|list
operator|.
name|isEnabled
argument_list|()
condition|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|list
operator|.
name|getTermList
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|result
argument_list|)
return|;
block|}
DECL|method|addProtectedTermsListFromFile (String fileName, boolean enabled)
specifier|public
name|void
name|addProtectedTermsListFromFile
parameter_list|(
name|String
name|fileName
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
try|try
block|{
name|mainList
operator|.
name|add
argument_list|(
name|readProtectedTermsListFromFile
argument_list|(
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|enabled
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FileNotFoundException
name|e
parameter_list|)
block|{
comment|// The file couldn't be found...
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot find protected terms file "
operator|+
name|fileName
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|readProtectedTermsListFromResource (String resource, String description, boolean enabled)
specifier|public
specifier|static
name|ProtectedTermsList
name|readProtectedTermsListFromResource
parameter_list|(
name|String
name|resource
parameter_list|,
name|String
name|description
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
name|ProtectedTermsParser
name|parser
init|=
operator|new
name|ProtectedTermsParser
argument_list|()
decl_stmt|;
name|parser
operator|.
name|readTermsFromResource
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|resource
argument_list|)
argument_list|,
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|description
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|parser
operator|.
name|getProtectTermsList
argument_list|(
name|enabled
argument_list|,
literal|true
argument_list|)
return|;
block|}
DECL|method|readProtectedTermsListFromFile (File file, boolean enabled)
specifier|public
specifier|static
name|ProtectedTermsList
name|readProtectedTermsListFromFile
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|enabled
parameter_list|)
throws|throws
name|FileNotFoundException
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Reading term list from file "
operator|+
name|file
argument_list|)
expr_stmt|;
name|ProtectedTermsParser
name|parser
init|=
operator|new
name|ProtectedTermsParser
argument_list|()
decl_stmt|;
name|parser
operator|.
name|readTermsFromFile
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|parser
operator|.
name|getProtectTermsList
argument_list|(
name|enabled
argument_list|,
literal|false
argument_list|)
return|;
block|}
DECL|method|readProtectedTermsListFromFile (File file, Charset encoding, boolean enabled)
specifier|public
specifier|static
name|ProtectedTermsList
name|readProtectedTermsListFromFile
parameter_list|(
name|File
name|file
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|boolean
name|enabled
parameter_list|)
throws|throws
name|FileNotFoundException
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Reading term list from file "
operator|+
name|file
argument_list|)
expr_stmt|;
name|ProtectedTermsParser
name|parser
init|=
operator|new
name|ProtectedTermsParser
argument_list|()
decl_stmt|;
name|parser
operator|.
name|readTermsFromFile
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|file
argument_list|)
argument_list|,
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|encoding
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|parser
operator|.
name|getProtectTermsList
argument_list|(
name|enabled
argument_list|,
literal|false
argument_list|)
return|;
block|}
DECL|method|removeProtectedTermsList (ProtectedTermsList termList)
specifier|public
name|boolean
name|removeProtectedTermsList
parameter_list|(
name|ProtectedTermsList
name|termList
parameter_list|)
block|{
name|termList
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
return|return
name|mainList
operator|.
name|remove
argument_list|(
name|termList
argument_list|)
return|;
block|}
DECL|method|addNewProtectedTermsList (String newDescription, String newLocation, boolean enabled)
specifier|public
name|ProtectedTermsList
name|addNewProtectedTermsList
parameter_list|(
name|String
name|newDescription
parameter_list|,
name|String
name|newLocation
parameter_list|,
name|boolean
name|enabled
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|newDescription
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|newLocation
argument_list|)
expr_stmt|;
name|ProtectedTermsList
name|resultingList
init|=
operator|new
name|ProtectedTermsList
argument_list|(
name|newDescription
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|,
name|newLocation
argument_list|)
decl_stmt|;
name|resultingList
operator|.
name|setEnabled
argument_list|(
name|enabled
argument_list|)
expr_stmt|;
name|resultingList
operator|.
name|createAndWriteHeading
argument_list|(
name|newDescription
argument_list|)
expr_stmt|;
name|mainList
operator|.
name|add
argument_list|(
name|resultingList
argument_list|)
expr_stmt|;
return|return
name|resultingList
return|;
block|}
DECL|method|addNewProtectedTermsList (String newDescription, String newLocation)
specifier|public
name|ProtectedTermsList
name|addNewProtectedTermsList
parameter_list|(
name|String
name|newDescription
parameter_list|,
name|String
name|newLocation
parameter_list|)
block|{
return|return
name|addNewProtectedTermsList
argument_list|(
name|newDescription
argument_list|,
name|newLocation
argument_list|,
literal|true
argument_list|)
return|;
block|}
block|}
end_class

end_unit

