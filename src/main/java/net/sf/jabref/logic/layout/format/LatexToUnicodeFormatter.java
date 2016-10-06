begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout.format
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
operator|.
name|format
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
name|LayoutFormatter
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
name|cleanup
operator|.
name|Formatter
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
name|strings
operator|.
name|LatexToUnicode
import|;
end_import

begin_comment
comment|/**  * This formatter converts LaTeX character sequences their equivalent unicode characters,  * and removes other LaTeX commands without handling them.  */
end_comment

begin_class
DECL|class|LatexToUnicodeFormatter
specifier|public
class|class
name|LatexToUnicodeFormatter
implements|implements
name|LayoutFormatter
implements|,
name|Formatter
block|{
DECL|field|formatter
specifier|private
specifier|final
name|LatexToUnicode
name|formatter
init|=
operator|new
name|LatexToUnicode
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"LaTeX to Unicode"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
literal|"latex_to_unicode"
return|;
block|}
annotation|@
name|Override
DECL|method|format (String inField)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|inField
parameter_list|)
block|{
return|return
name|formatter
operator|.
name|format
argument_list|(
name|inField
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Converts LaTeX encoding to Unicode characters."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getExampleInput ()
specifier|public
name|String
name|getExampleInput
parameter_list|()
block|{
return|return
literal|"M{\\\"{o}}nch"
return|;
block|}
block|}
end_class

end_unit

