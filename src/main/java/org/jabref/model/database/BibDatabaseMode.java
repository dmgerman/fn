begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_comment
comment|/**  * An enum which contains the possible {@link BibDatabase} Modes.  * Possible are BibTeX and biblatex.  */
end_comment

begin_enum
DECL|enum|BibDatabaseMode
specifier|public
enum|enum
name|BibDatabaseMode
block|{
DECL|enumConstant|BIBTEX
name|BIBTEX
block|,
DECL|enumConstant|BIBLATEX
name|BIBLATEX
block|;
comment|/**      * @return the name of the current mode as String      */
DECL|method|getFormattedName ()
specifier|public
name|String
name|getFormattedName
parameter_list|()
block|{
if|if
condition|(
name|this
operator|==
name|BIBTEX
condition|)
block|{
return|return
literal|"BibTeX"
return|;
block|}
else|else
block|{
return|return
literal|"biblatex"
return|;
block|}
block|}
comment|/**      * Returns the opposite mode of the current mode as {@link BibDatabaseMode}.      *      * @return biblatex if the current mode is BIBTEX, BibTeX else      */
DECL|method|getOppositeMode ()
specifier|public
name|BibDatabaseMode
name|getOppositeMode
parameter_list|()
block|{
if|if
condition|(
name|this
operator|==
name|BIBTEX
condition|)
block|{
return|return
name|BIBLATEX
return|;
block|}
else|else
block|{
return|return
name|BIBTEX
return|;
block|}
block|}
comment|/**      * Returns the {@link BibDatabaseMode} that equals the given string. The use of capital and small letters      * in the string doesn't matter.If neither "bibtex" nor "biblatex" is the given string, then an      * {@link IllegalArgumentException} will be thrown.      *      * @return  BIBTEX, if the string is bibtex<br>      *          BIBLATEX, if the string is biblatex<br>      */
DECL|method|parse (String data)
specifier|public
specifier|static
name|BibDatabaseMode
name|parse
parameter_list|(
name|String
name|data
parameter_list|)
block|{
return|return
name|BibDatabaseMode
operator|.
name|valueOf
argument_list|(
name|data
operator|.
name|toUpperCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * @return The current mode as String in lowercase      */
DECL|method|getAsString ()
specifier|public
name|String
name|getAsString
parameter_list|()
block|{
return|return
name|getFormattedName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

