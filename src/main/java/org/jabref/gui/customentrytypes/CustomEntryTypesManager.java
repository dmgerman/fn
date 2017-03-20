begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customentrytypes
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customentrytypes
package|;
end_package

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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|EntryTypes
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|CustomEntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|CustomEntryTypesManager
specifier|public
class|class
name|CustomEntryTypesManager
block|{
DECL|method|CustomEntryTypesManager ()
specifier|private
name|CustomEntryTypesManager
parameter_list|()
block|{     }
comment|/**      * Iterate through all entry types, and store those that are      * custom defined to preferences. This method is called from      * JabRefFrame when the program closes.      */
DECL|method|saveCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|saveCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|saveCustomEntryTypes
argument_list|(
name|prefs
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|saveCustomEntryTypes
argument_list|(
name|prefs
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
expr_stmt|;
block|}
DECL|method|saveCustomEntryTypes (JabRefPreferences prefs, BibDatabaseMode mode)
specifier|private
specifier|static
name|void
name|saveCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|List
argument_list|<
name|CustomEntryType
argument_list|>
name|customBiblatexTypes
init|=
name|EntryTypes
operator|.
name|getAllValues
argument_list|(
name|mode
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|type
lambda|->
name|type
operator|instanceof
name|CustomEntryType
argument_list|)
operator|.
name|map
argument_list|(
name|entryType
lambda|->
operator|(
name|CustomEntryType
operator|)
name|entryType
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|prefs
operator|.
name|storeCustomEntryTypes
argument_list|(
name|customBiblatexTypes
argument_list|,
name|mode
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

