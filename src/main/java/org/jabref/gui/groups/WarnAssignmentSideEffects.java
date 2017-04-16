begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
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
name|InternalBibtexFields
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
name|groups
operator|.
name|AbstractGroup
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
name|groups
operator|.
name|KeywordGroup
import|;
end_import

begin_class
DECL|class|WarnAssignmentSideEffects
specifier|public
class|class
name|WarnAssignmentSideEffects
block|{
DECL|method|WarnAssignmentSideEffects ()
specifier|private
name|WarnAssignmentSideEffects
parameter_list|()
block|{     }
comment|/**      * Warns the user of undesired side effects of an explicit assignment/removal of entries to/from this group.      * Currently there are four types of groups: AllEntriesGroup, SearchGroup - do not support explicit assignment.      * ExplicitGroup and KeywordGroup - this modifies entries upon assignment/removal.      * Modifications are acceptable unless they affect a standard field (such as "author") besides the "keywords" or "groups' field.      *      * @param parent The Component used as a parent when displaying a confirmation dialog.      * @return true if the assignment has no undesired side effects, or the user chose to perform it anyway. false      * otherwise (this indicates that the user has aborted the assignment).      */
DECL|method|warnAssignmentSideEffects (List<AbstractGroup> groups, Component parent)
specifier|public
specifier|static
name|boolean
name|warnAssignmentSideEffects
parameter_list|(
name|List
argument_list|<
name|AbstractGroup
argument_list|>
name|groups
parameter_list|,
name|Component
name|parent
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|affectedFields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|AbstractGroup
name|group
range|:
name|groups
control|)
block|{
if|if
condition|(
name|group
operator|instanceof
name|KeywordGroup
condition|)
block|{
name|KeywordGroup
name|keywordGroup
init|=
operator|(
name|KeywordGroup
operator|)
name|group
decl_stmt|;
name|String
name|field
init|=
name|keywordGroup
operator|.
name|getSearchField
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
decl_stmt|;
if|if
condition|(
name|FieldName
operator|.
name|KEYWORDS
operator|.
name|equals
argument_list|(
name|field
argument_list|)
operator|||
name|FieldName
operator|.
name|GROUPS
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
continue|continue;
comment|// this is not undesired
block|}
for|for
control|(
name|String
name|fieldName
range|:
name|InternalBibtexFields
operator|.
name|getAllPublicFieldNames
argument_list|()
control|)
block|{
if|if
condition|(
name|field
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|affectedFields
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
if|if
condition|(
name|affectedFields
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
comment|// no side effects
block|}
comment|// show a warning, then return
name|StringBuilder
name|message
init|=
operator|new
name|StringBuilder
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This action will modify the following field(s) in at least one entry each:"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|affectedField
range|:
name|affectedFields
control|)
block|{
name|message
operator|.
name|append
argument_list|(
name|affectedField
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|message
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This could cause undesired changes to your entries."
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
operator|.
name|append
argument_list|(
literal|"It is recommended that you change the grouping field in your group definition to \"keywords\" or a non-standard name."
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n\n"
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you still want to continue?"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|choice
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|parent
argument_list|,
name|message
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warning"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
decl_stmt|;
return|return
name|choice
operator|!=
name|JOptionPane
operator|.
name|NO_OPTION
return|;
comment|// if (groups instanceof KeywordGroup) {
comment|// KeywordGroup kg = (KeywordGroup) groups;
comment|// String field = kg.getSearchField().toLowerCase(Locale.ROOT);
comment|// if (field.equals("keywords"))
comment|// return true; // this is not undesired
comment|// for (int i = 0; i< GUIGlobals.ALL_FIELDS.length; ++i) {
comment|// if (field.equals(GUIGlobals.ALL_FIELDS[i])) {
comment|// // show a warning, then return
comment|// String message = Globals ...
comment|// .lang(
comment|// "This action will modify the \"%0\" field "
comment|// + "of your entries.\nThis could cause undesired changes to "
comment|// + "your entries, so it is\nrecommended that you change the grouping
comment|// field "
comment|// + "in your group\ndefinition to \"keywords\" or a non-standard name."
comment|// + "\n\nDo you still want to continue?",
comment|// field);
comment|// int choice = JOptionPane.showConfirmDialog(parent, message,
comment|// Globals.lang("Warning"), JOptionPane.YES_NO_OPTION,
comment|// JOptionPane.WARNING_MESSAGE);
comment|// return choice != JOptionPane.NO_OPTION;
comment|// }
comment|// }
comment|// }
comment|// return true; // found no side effects
block|}
DECL|method|warnAssignmentSideEffects (AbstractGroup group, Component parent)
specifier|public
specifier|static
name|boolean
name|warnAssignmentSideEffects
parameter_list|(
name|AbstractGroup
name|group
parameter_list|,
name|Component
name|parent
parameter_list|)
block|{
return|return
name|warnAssignmentSideEffects
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|group
argument_list|)
argument_list|,
name|parent
argument_list|)
return|;
block|}
block|}
end_class

end_unit

