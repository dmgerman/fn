begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.keyboard
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
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

begin_enum
DECL|enum|KeyBindingCategory
specifier|public
enum|enum
name|KeyBindingCategory
block|{
DECL|enumConstant|FILE
DECL|enumConstant|Localization.lang
name|FILE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|EDIT
DECL|enumConstant|Localization.lang
name|EDIT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|SEARCH
DECL|enumConstant|Localization.lang
name|SEARCH
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|VIEW
DECL|enumConstant|Localization.lang
name|VIEW
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"View"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|BIBTEX
DECL|enumConstant|BibDatabaseMode.BIBTEX.getFormattedName
name|BIBTEX
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
operator|.
name|getFormattedName
argument_list|()
argument_list|)
block|,
DECL|enumConstant|QUALITY
DECL|enumConstant|Localization.lang
name|QUALITY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quality"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|TOOLS
DECL|enumConstant|Localization.lang
name|TOOLS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tools"
argument_list|)
argument_list|)
block|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|method|KeyBindingCategory (String name)
specifier|private
name|KeyBindingCategory
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
block|}
end_enum

end_unit

