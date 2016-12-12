begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer.actions
package|package
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
name|actions
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Font
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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BoxLayout
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|logic
operator|.
name|CustomEntryTypesManager
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
name|ParserResult
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
name|EntryTypes
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
name|BibDatabaseMode
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
name|CustomEntryType
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
name|EntryType
import|;
end_import

begin_comment
comment|/**  * This action checks whether any new custom entry types were loaded from this  * BIB file. If so, an offer to remember these entry types is given.  */
end_comment

begin_class
DECL|class|CheckForNewEntryTypesAction
specifier|public
class|class
name|CheckForNewEntryTypesAction
implements|implements
name|PostOpenAction
block|{
annotation|@
name|Override
DECL|method|isActionNecessary (ParserResult parserResult)
specifier|public
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
return|return
operator|!
name|getListOfUnknownAndUnequalCustomizations
argument_list|(
name|parserResult
argument_list|)
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|performAction (BasePanel panel, ParserResult parserResult)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|BibDatabaseMode
name|mode
init|=
name|parserResult
operator|.
name|getMetaData
argument_list|()
operator|.
name|getMode
argument_list|()
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|EntryType
argument_list|>
name|typesToStore
init|=
name|determineEntryTypesToSave
argument_list|(
name|panel
argument_list|,
name|getListOfUnknownAndUnequalCustomizations
argument_list|(
name|parserResult
argument_list|)
argument_list|,
name|mode
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|typesToStore
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|typesToStore
operator|.
name|forEach
argument_list|(
name|type
lambda|->
name|EntryTypes
operator|.
name|addOrModifyCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|type
argument_list|,
name|mode
argument_list|)
argument_list|)
expr_stmt|;
name|CustomEntryTypesManager
operator|.
name|saveCustomEntryTypes
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getListOfUnknownAndUnequalCustomizations (ParserResult parserResult)
specifier|private
name|List
argument_list|<
name|EntryType
argument_list|>
name|getListOfUnknownAndUnequalCustomizations
parameter_list|(
name|ParserResult
name|parserResult
parameter_list|)
block|{
name|BibDatabaseMode
name|mode
init|=
name|parserResult
operator|.
name|getMetaData
argument_list|()
operator|.
name|getMode
argument_list|()
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultBibDatabaseMode
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|parserResult
operator|.
name|getEntryTypes
argument_list|()
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|type
lambda|->
operator|(
operator|!
name|EntryTypes
operator|.
name|getType
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|,
name|mode
argument_list|)
operator|.
name|isPresent
argument_list|()
operator|)
operator|||
operator|!
name|EntryTypes
operator|.
name|isEqualNameAndFieldBased
argument_list|(
name|type
argument_list|,
name|EntryTypes
operator|.
name|getType
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|,
name|mode
argument_list|)
operator|.
name|get
argument_list|()
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
return|;
block|}
DECL|method|determineEntryTypesToSave (BasePanel panel, List<EntryType> allCustomizedEntryTypes, BibDatabaseMode databaseMode)
specifier|private
name|List
argument_list|<
name|EntryType
argument_list|>
name|determineEntryTypesToSave
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|List
argument_list|<
name|EntryType
argument_list|>
name|allCustomizedEntryTypes
parameter_list|,
name|BibDatabaseMode
name|databaseMode
parameter_list|)
block|{
name|List
argument_list|<
name|EntryType
argument_list|>
name|newTypes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|EntryType
argument_list|>
name|differentCustomizations
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|EntryType
name|customType
range|:
name|allCustomizedEntryTypes
control|)
block|{
if|if
condition|(
operator|!
name|EntryTypes
operator|.
name|getType
argument_list|(
name|customType
operator|.
name|getName
argument_list|()
argument_list|,
name|databaseMode
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|newTypes
operator|.
name|add
argument_list|(
name|customType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|EntryType
name|currentlyStoredType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|customType
operator|.
name|getName
argument_list|()
argument_list|,
name|databaseMode
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|EntryTypes
operator|.
name|isEqualNameAndFieldBased
argument_list|(
name|customType
argument_list|,
name|currentlyStoredType
argument_list|)
condition|)
block|{
name|differentCustomizations
operator|.
name|add
argument_list|(
name|customType
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Map
argument_list|<
name|EntryType
argument_list|,
name|JCheckBox
argument_list|>
name|typeCheckBoxMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|JPanel
name|checkboxPanel
init|=
name|createCheckBoxPanel
argument_list|(
name|newTypes
argument_list|,
name|differentCustomizations
argument_list|,
name|typeCheckBoxMap
argument_list|)
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|checkboxPanel
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Custom entry types"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
return|return
name|typeCheckBoxMap
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|getValue
argument_list|()
operator|.
name|isSelected
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|Map
operator|.
name|Entry
operator|::
name|getKey
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|method|createCheckBoxPanel (List<EntryType> newTypes, List<EntryType> differentCustomizations, Map<EntryType, JCheckBox> typeCheckBoxMap)
specifier|private
name|JPanel
name|createCheckBoxPanel
parameter_list|(
name|List
argument_list|<
name|EntryType
argument_list|>
name|newTypes
parameter_list|,
name|List
argument_list|<
name|EntryType
argument_list|>
name|differentCustomizations
parameter_list|,
name|Map
argument_list|<
name|EntryType
argument_list|,
name|JCheckBox
argument_list|>
name|typeCheckBoxMap
parameter_list|)
block|{
name|JPanel
name|checkboxPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|checkboxPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|checkboxPanel
argument_list|,
name|BoxLayout
operator|.
name|PAGE_AXIS
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|customFoundLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Custom entry types found in file"
argument_list|)
operator|+
literal|"."
argument_list|)
decl_stmt|;
name|Font
name|boldStandardFont
init|=
operator|new
name|Font
argument_list|(
name|customFoundLabel
operator|.
name|getFont
argument_list|()
operator|.
name|getFontName
argument_list|()
argument_list|,
name|Font
operator|.
name|BOLD
argument_list|,
name|customFoundLabel
operator|.
name|getFont
argument_list|()
operator|.
name|getSize
argument_list|()
argument_list|)
decl_stmt|;
name|customFoundLabel
operator|.
name|setFont
argument_list|(
name|boldStandardFont
argument_list|)
expr_stmt|;
name|checkboxPanel
operator|.
name|add
argument_list|(
name|customFoundLabel
argument_list|)
expr_stmt|;
name|JLabel
name|selectLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select all customized types to be stored in local preferences"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|selectLabel
operator|.
name|setFont
argument_list|(
name|boldStandardFont
argument_list|)
expr_stmt|;
name|checkboxPanel
operator|.
name|add
argument_list|(
name|selectLabel
argument_list|)
expr_stmt|;
name|checkboxPanel
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
literal|" "
argument_list|)
argument_list|)
expr_stmt|;
comment|// add all unknown types:
if|if
condition|(
operator|!
name|newTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|checkboxPanel
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Currently unknown"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|EntryType
name|type
range|:
name|newTypes
control|)
block|{
name|JCheckBox
name|box
init|=
operator|new
name|JCheckBox
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|checkboxPanel
operator|.
name|add
argument_list|(
name|box
argument_list|)
expr_stmt|;
name|typeCheckBoxMap
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|box
argument_list|)
expr_stmt|;
block|}
block|}
comment|// add all different customizations
if|if
condition|(
operator|!
name|differentCustomizations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|checkboxPanel
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Different Customization, current settings will be overwritten"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|EntryType
name|type
range|:
name|differentCustomizations
control|)
block|{
name|JCheckBox
name|box
init|=
operator|new
name|JCheckBox
argument_list|(
name|type
operator|.
name|getName
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|checkboxPanel
operator|.
name|add
argument_list|(
name|box
argument_list|)
expr_stmt|;
name|typeCheckBoxMap
operator|.
name|put
argument_list|(
name|type
argument_list|,
name|box
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|checkboxPanel
return|;
block|}
block|}
end_class

end_unit

