begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.mergeentries
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|mergeentries
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|Optional
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
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
name|gui
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|ISBNtoBibTeXFetcher
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
name|logic
operator|.
name|importer
operator|.
name|FetcherException
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
name|logic
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|ArXiv
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
name|logic
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|DOItoBibTeX
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|FieldName
import|;
end_import

begin_comment
comment|/**  * Class for fetching and merging information based on a specific field  *  */
end_comment

begin_class
DECL|class|FetchAndMergeEntry
specifier|public
class|class
name|FetchAndMergeEntry
block|{
comment|// A list of all field which are supported
DECL|field|SUPPORTED_FIELDS
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|SUPPORTED_FIELDS
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|,
name|FieldName
operator|.
name|EPRINT
argument_list|,
name|FieldName
operator|.
name|ISBN
argument_list|)
decl_stmt|;
comment|/**      * Convenience constructor for a single field      *      * @param entry - BibEntry to fetch information for      * @param panel - current BasePanel      * @param field - field to get information from      */
DECL|method|FetchAndMergeEntry (BibEntry entry, BasePanel panel, String field)
specifier|public
name|FetchAndMergeEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|this
argument_list|(
name|entry
argument_list|,
name|panel
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|field
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Default constructor      *      * @param entry - BibEntry to fetch information for      * @param panel - current BasePanel      * @param fields - List of fields to get information from, one at a time in given order      */
DECL|method|FetchAndMergeEntry (BibEntry entry, BasePanel panel, List<String> fields)
specifier|public
name|FetchAndMergeEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|fieldContent
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// Get better looking name for status messages
name|String
name|type
init|=
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldContent
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|// Get entry based on field
if|if
condition|(
name|FieldName
operator|.
name|DOI
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|fetchedEntry
operator|=
operator|new
name|DOItoBibTeX
argument_list|()
operator|.
name|getEntryFromDOI
argument_list|(
name|fieldContent
operator|.
name|get
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|ISBN
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|fetchedEntry
operator|=
operator|new
name|ISBNtoBibTeXFetcher
argument_list|()
operator|.
name|getEntryFromISBN
argument_list|(
name|fieldContent
operator|.
name|get
argument_list|()
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|EPRINT
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
try|try
block|{
name|fetchedEntry
operator|=
operator|new
name|ArXiv
argument_list|()
operator|.
name|performSearchById
argument_list|(
name|fieldContent
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|e
parameter_list|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot get info based on given %0:_%1"
argument_list|,
name|type
argument_list|,
name|fieldContent
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|fetchedEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|MergeFetchedEntryDialog
name|dialog
init|=
operator|new
name|MergeFetchedEntryDialog
argument_list|(
name|panel
argument_list|,
name|entry
argument_list|,
name|fetchedEntry
operator|.
name|get
argument_list|()
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot get info based on given %0:_%1"
argument_list|,
name|type
argument_list|,
name|fieldContent
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No %0 found"
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getDisplayNameOfSupportedFields ()
specifier|public
specifier|static
name|String
name|getDisplayNameOfSupportedFields
parameter_list|()
block|{
return|return
name|FieldName
operator|.
name|orFields
argument_list|(
name|SUPPORTED_FIELDS
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|fieldName
lambda|->
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|fieldName
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

