begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * Formatter normalizing a list of person names to the BibTeX format.  */
end_comment

begin_class
DECL|class|NormalizeNamesFormatter
specifier|public
class|class
name|NormalizeNamesFormatter
implements|implements
name|Formatter
block|{
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
literal|"Normalize names of persons"
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
literal|"normalize_names"
return|;
block|}
annotation|@
name|Override
DECL|method|format (String nameList)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|nameList
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|nameList
argument_list|)
expr_stmt|;
name|AuthorList
name|authorList
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|nameList
argument_list|)
decl_stmt|;
return|return
name|authorList
operator|.
name|getAsLastFirstNamesWithAnd
argument_list|(
literal|false
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
literal|"Normalizes lists of persons to the BibTeX standard."
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
literal|"Albert Einstein and Alan Turing"
return|;
block|}
block|}
end_class

end_unit

