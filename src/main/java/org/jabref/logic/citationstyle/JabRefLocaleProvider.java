begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.citationstyle
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|citationstyle
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UncheckedIOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|Map
import|;
end_import

begin_import
import|import
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|LocaleProvider
import|;
end_import

begin_import
import|import
name|de
operator|.
name|undercouch
operator|.
name|citeproc
operator|.
name|helper
operator|.
name|CSLUtils
import|;
end_import

begin_comment
comment|/**  * A {@link LocaleProvider} that loads locales from a directory in the current module.  *  * This implementation is only a slight adaption of {@link de.undercouch.citeproc.DefaultLocaleProvider}.  */
end_comment

begin_class
DECL|class|JabRefLocaleProvider
specifier|public
class|class
name|JabRefLocaleProvider
implements|implements
name|LocaleProvider
block|{
DECL|field|LOCALES_ROOT
specifier|private
specifier|static
specifier|final
name|String
name|LOCALES_ROOT
init|=
literal|"/csl-locales"
decl_stmt|;
DECL|field|locales
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|locales
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|retrieveLocale (String lang)
specifier|public
name|String
name|retrieveLocale
parameter_list|(
name|String
name|lang
parameter_list|)
block|{
return|return
name|locales
operator|.
name|computeIfAbsent
argument_list|(
name|lang
argument_list|,
name|locale
lambda|->
block|{
try|try
block|{
name|URL
name|url
init|=
name|getClass
argument_list|()
operator|.
name|getResource
argument_list|(
name|LOCALES_ROOT
operator|+
literal|"/locales-"
operator|+
name|locale
operator|+
literal|".xml"
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"Unable to load locale "
operator|+
name|locale
argument_list|)
throw|;
block|}
return|return
name|CSLUtils
operator|.
name|readURLToString
argument_list|(
name|url
argument_list|,
literal|"UTF-8"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|UncheckedIOException
argument_list|(
literal|"failed to read locale "
operator|+
name|locale
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
argument_list|)
return|;
block|}
block|}
end_class

end_unit

