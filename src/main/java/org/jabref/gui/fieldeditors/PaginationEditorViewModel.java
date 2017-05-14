begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|BiMap
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
name|collect
operator|.
name|HashBiMap
import|;
end_import

begin_class
DECL|class|PaginationEditorViewModel
specifier|public
class|class
name|PaginationEditorViewModel
extends|extends
name|MapBasedEditorViewModel
argument_list|<
name|String
argument_list|>
block|{
DECL|field|itemMap
specifier|private
name|BiMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|itemMap
init|=
name|HashBiMap
operator|.
name|create
argument_list|(
literal|7
argument_list|)
decl_stmt|;
DECL|method|PaginationEditorViewModel ()
specifier|public
name|PaginationEditorViewModel
parameter_list|()
block|{
name|itemMap
operator|.
name|put
argument_list|(
literal|"page"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Page"
argument_list|)
argument_list|)
expr_stmt|;
name|itemMap
operator|.
name|put
argument_list|(
literal|"column"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Column"
argument_list|)
argument_list|)
expr_stmt|;
name|itemMap
operator|.
name|put
argument_list|(
literal|"line"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Line"
argument_list|)
argument_list|)
expr_stmt|;
name|itemMap
operator|.
name|put
argument_list|(
literal|"verse"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Verse"
argument_list|)
argument_list|)
expr_stmt|;
name|itemMap
operator|.
name|put
argument_list|(
literal|"section"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Section"
argument_list|)
argument_list|)
expr_stmt|;
name|itemMap
operator|.
name|put
argument_list|(
literal|"paragraph"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paragraph"
argument_list|)
argument_list|)
expr_stmt|;
name|itemMap
operator|.
name|put
argument_list|(
literal|"none"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"None"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getItemMap ()
specifier|protected
name|BiMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getItemMap
parameter_list|()
block|{
return|return
name|itemMap
return|;
block|}
annotation|@
name|Override
DECL|method|convertToDisplayText (String object)
specifier|public
name|String
name|convertToDisplayText
parameter_list|(
name|String
name|object
parameter_list|)
block|{
return|return
name|object
return|;
block|}
block|}
end_class

end_unit
