begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
package|;
end_package

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
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|journals
operator|.
name|JournalAbbreviationPreferences
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
name|layout
operator|.
name|format
operator|.
name|FileLinkPreferences
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
name|layout
operator|.
name|format
operator|.
name|NameFormatterPreferences
import|;
end_import

begin_class
DECL|class|LayoutFormatterPreferences
specifier|public
class|class
name|LayoutFormatterPreferences
block|{
DECL|field|nameFormatterPreferences
specifier|private
specifier|final
name|NameFormatterPreferences
name|nameFormatterPreferences
decl_stmt|;
DECL|field|journalAbbreviationPreferences
specifier|private
specifier|final
name|JournalAbbreviationPreferences
name|journalAbbreviationPreferences
decl_stmt|;
DECL|field|fileLinkPreferences
specifier|private
specifier|final
name|FileLinkPreferences
name|fileLinkPreferences
decl_stmt|;
DECL|field|customExportNameFormatters
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|customExportNameFormatters
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|journalAbbreviationLoader
specifier|private
specifier|final
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
decl_stmt|;
DECL|method|LayoutFormatterPreferences (NameFormatterPreferences nameFormatterPreferences, JournalAbbreviationPreferences journalAbbreviationPreferences, FileLinkPreferences fileLinkPreferences, JournalAbbreviationLoader journalAbbreviationLoader)
specifier|public
name|LayoutFormatterPreferences
parameter_list|(
name|NameFormatterPreferences
name|nameFormatterPreferences
parameter_list|,
name|JournalAbbreviationPreferences
name|journalAbbreviationPreferences
parameter_list|,
name|FileLinkPreferences
name|fileLinkPreferences
parameter_list|,
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
parameter_list|)
block|{
name|this
operator|.
name|nameFormatterPreferences
operator|=
name|nameFormatterPreferences
expr_stmt|;
name|this
operator|.
name|journalAbbreviationPreferences
operator|=
name|journalAbbreviationPreferences
expr_stmt|;
name|this
operator|.
name|fileLinkPreferences
operator|=
name|fileLinkPreferences
expr_stmt|;
name|this
operator|.
name|journalAbbreviationLoader
operator|=
name|journalAbbreviationLoader
expr_stmt|;
block|}
DECL|method|getNameFormatterPreferences ()
specifier|public
name|NameFormatterPreferences
name|getNameFormatterPreferences
parameter_list|()
block|{
return|return
name|nameFormatterPreferences
return|;
block|}
DECL|method|getJournalAbbreviationPreferences ()
specifier|public
name|JournalAbbreviationPreferences
name|getJournalAbbreviationPreferences
parameter_list|()
block|{
return|return
name|journalAbbreviationPreferences
return|;
block|}
DECL|method|getFileLinkPreferences ()
specifier|public
name|FileLinkPreferences
name|getFileLinkPreferences
parameter_list|()
block|{
return|return
name|fileLinkPreferences
return|;
block|}
DECL|method|getJournalAbbreviationLoader ()
specifier|public
name|JournalAbbreviationLoader
name|getJournalAbbreviationLoader
parameter_list|()
block|{
return|return
name|journalAbbreviationLoader
return|;
block|}
DECL|method|clearCustomExportNameFormatters ()
specifier|public
name|void
name|clearCustomExportNameFormatters
parameter_list|()
block|{
name|customExportNameFormatters
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
DECL|method|putCustomExportNameFormatter (String formatterName, String contents)
specifier|public
name|void
name|putCustomExportNameFormatter
parameter_list|(
name|String
name|formatterName
parameter_list|,
name|String
name|contents
parameter_list|)
block|{
name|customExportNameFormatters
operator|.
name|put
argument_list|(
name|formatterName
argument_list|,
name|contents
argument_list|)
expr_stmt|;
block|}
DECL|method|getCustomExportNameFormatter (String formatterName)
specifier|public
name|Optional
argument_list|<
name|String
argument_list|>
name|getCustomExportNameFormatter
parameter_list|(
name|String
name|formatterName
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|customExportNameFormatters
operator|.
name|get
argument_list|(
name|formatterName
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

