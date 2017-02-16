begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.util.comparator
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|comparator
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|TypedBibEntry
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
name|BibDatabaseContext
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
name|BibEntry
import|;
end_import

begin_class
DECL|class|FirstColumnComparator
specifier|public
class|class
name|FirstColumnComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
DECL|field|database
specifier|private
specifier|final
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|method|FirstColumnComparator (BibDatabaseContext database)
specifier|public
name|FirstColumnComparator
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibEntry e1, BibEntry e2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibEntry
name|e1
parameter_list|,
name|BibEntry
name|e2
parameter_list|)
block|{
name|int
name|score1
init|=
literal|0
decl_stmt|;
name|int
name|score2
init|=
literal|0
decl_stmt|;
name|TypedBibEntry
name|typedEntry1
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|e1
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|TypedBibEntry
name|typedEntry2
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|e2
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|typedEntry1
operator|.
name|hasAllRequiredFields
argument_list|()
condition|)
block|{
name|score1
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|typedEntry2
operator|.
name|hasAllRequiredFields
argument_list|()
condition|)
block|{
name|score2
operator|++
expr_stmt|;
block|}
return|return
name|score1
operator|-
name|score2
return|;
block|}
block|}
end_class

end_unit

