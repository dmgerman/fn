begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FilterReader
import|;
end_import

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
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  *  * Warning -- it is not a generic filter, only read is implemented!  *  * Note: this is just a quick port of the original SPIRESBibtexFilterReader.  *  * @author Fedor Bezrukov  * @author Sheer El-Showk  *  * @version $Id$  *  * TODO: Fix grammar in bibtex entries -- it ma return invalid bibkeys (with space)  *  */
end_comment

begin_class
DECL|class|INSPIREBibtexFilterReader
specifier|public
class|class
name|INSPIREBibtexFilterReader
extends|extends
name|FilterReader
block|{
DECL|field|inReader
specifier|private
specifier|final
name|BufferedReader
name|inReader
decl_stmt|;
DECL|field|line
specifier|private
name|String
name|line
decl_stmt|;
DECL|field|pos
specifier|private
name|int
name|pos
decl_stmt|;
DECL|field|pre
specifier|private
name|boolean
name|pre
decl_stmt|;
DECL|field|PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"@Article\\{.*,"
argument_list|)
decl_stmt|;
DECL|method|INSPIREBibtexFilterReader (final Reader initialReader)
specifier|public
name|INSPIREBibtexFilterReader
parameter_list|(
specifier|final
name|Reader
name|initialReader
parameter_list|)
block|{
name|super
argument_list|(
name|initialReader
argument_list|)
expr_stmt|;
name|inReader
operator|=
operator|new
name|BufferedReader
argument_list|(
name|initialReader
argument_list|)
expr_stmt|;
name|pos
operator|=
operator|-
literal|1
expr_stmt|;
name|pre
operator|=
literal|false
expr_stmt|;
block|}
DECL|method|readpreLine ()
specifier|private
name|String
name|readpreLine
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|l
decl_stmt|;
do|do
block|{
name|l
operator|=
name|inReader
operator|.
name|readLine
argument_list|()
expr_stmt|;
if|if
condition|(
name|l
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|l
operator|.
name|contains
argument_list|(
literal|"<pre>"
argument_list|)
condition|)
block|{
name|pre
operator|=
literal|true
expr_stmt|;
name|l
operator|=
name|inReader
operator|.
name|readLine
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|l
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|l
operator|.
name|contains
argument_list|(
literal|"</pre>"
argument_list|)
condition|)
block|{
name|pre
operator|=
literal|false
expr_stmt|;
block|}
block|}
do|while
condition|(
operator|!
name|pre
condition|)
do|;
return|return
name|l
return|;
block|}
DECL|method|fixBibkey (final String preliminaryLine)
specifier|private
name|String
name|fixBibkey
parameter_list|(
specifier|final
name|String
name|preliminaryLine
parameter_list|)
block|{
if|if
condition|(
name|preliminaryLine
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|PATTERN
operator|.
name|matcher
argument_list|(
name|preliminaryLine
argument_list|)
operator|.
name|find
argument_list|()
condition|)
block|{
return|return
name|preliminaryLine
operator|.
name|replace
argument_list|(
literal|' '
argument_list|,
literal|'_'
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|preliminaryLine
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|read ()
specifier|public
name|int
name|read
parameter_list|()
throws|throws
name|IOException
block|{
if|if
condition|(
name|pos
operator|<
literal|0
condition|)
block|{
name|line
operator|=
name|fixBibkey
argument_list|(
name|readpreLine
argument_list|()
argument_list|)
expr_stmt|;
name|pos
operator|=
literal|0
expr_stmt|;
if|if
condition|(
name|line
operator|==
literal|null
condition|)
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
if|if
condition|(
name|pos
operator|>=
name|line
operator|.
name|length
argument_list|()
condition|)
block|{
name|pos
operator|=
operator|-
literal|1
expr_stmt|;
return|return
literal|'\n'
return|;
block|}
return|return
name|line
operator|.
name|charAt
argument_list|(
name|pos
operator|++
argument_list|)
return|;
block|}
block|}
end_class

end_unit
