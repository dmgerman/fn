begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors.contextmenu
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|contextmenu
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|formatter
operator|.
name|Formatters
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
name|cleanup
operator|.
name|Formatter
import|;
end_import

begin_class
DECL|class|CaseChangeMenu
specifier|public
class|class
name|CaseChangeMenu
extends|extends
name|JMenu
block|{
DECL|method|CaseChangeMenu (final JTextComponent parent)
specifier|public
name|CaseChangeMenu
parameter_list|(
specifier|final
name|JTextComponent
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change case"
argument_list|)
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parent
argument_list|)
expr_stmt|;
comment|// create menu items, one for each case changer
for|for
control|(
specifier|final
name|Formatter
name|caseChanger
range|:
name|Formatters
operator|.
name|CASE_CHANGERS
control|)
block|{
name|JMenuItem
name|menuItem
init|=
operator|new
name|JMenuItem
argument_list|(
name|caseChanger
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|menuItem
operator|.
name|setToolTipText
argument_list|(
name|caseChanger
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|menuItem
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|parent
operator|.
name|setText
argument_list|(
name|caseChanger
operator|.
name|format
argument_list|(
name|parent
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|menuItem
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
