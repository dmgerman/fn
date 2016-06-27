begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.keyboard
package|package
name|net
operator|.
name|sf
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

begin_enum
DECL|enum|KeyBindingCategory
specifier|public
enum|enum
name|KeyBindingCategory
block|{
DECL|enumConstant|FILE
name|FILE
argument_list|(
DECL|enumConstant|Localization.lang
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|EDIT
name|EDIT
argument_list|(
DECL|enumConstant|Localization.lang
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|SEARCH
name|SEARCH
argument_list|(
DECL|enumConstant|Localization.lang
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|VIEW
name|VIEW
argument_list|(
DECL|enumConstant|Localization.lang
name|Localization
operator|.
name|lang
argument_list|(
literal|"View"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|BIBTEX
name|BIBTEX
argument_list|(
DECL|enumConstant|Localization.lang
name|Localization
operator|.
name|lang
argument_list|(
literal|"BibTeX"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|QUALITY
name|QUALITY
argument_list|(
DECL|enumConstant|Localization.lang
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quality"
argument_list|)
argument_list|)
block|,
DECL|enumConstant|TOOLS
name|TOOLS
argument_list|(
DECL|enumConstant|Localization.lang
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

