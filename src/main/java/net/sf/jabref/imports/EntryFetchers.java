begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|imports
operator|.
name|fetcher
operator|.
name|ISBNtoBibTeXFetcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_class
DECL|class|EntryFetchers
specifier|public
class|class
name|EntryFetchers
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
specifier|final
name|EntryFetchers
name|INSTANCE
init|=
operator|new
name|EntryFetchers
argument_list|()
decl_stmt|;
DECL|field|entryFetchers
specifier|private
specifier|final
name|List
argument_list|<
name|EntryFetcher
argument_list|>
name|entryFetchers
init|=
operator|new
name|LinkedList
argument_list|<
name|EntryFetcher
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|EntryFetchers ()
specifier|public
name|EntryFetchers
parameter_list|()
block|{
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|ADSFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|CiteSeerXFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|DBLPFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|DiVAtoBibTeXFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|DOItoBibTeXFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|IEEEXploreFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|INSPIREFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|ISBNtoBibTeXFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|JSTORFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|JSTORFetcher2
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|MedlineFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|OAI2Fetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|ScienceDirectFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SPIRESFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|ACMPortalFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|GoogleScholarFetcher
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getEntryFetchers ()
specifier|public
name|List
argument_list|<
name|EntryFetcher
argument_list|>
name|getEntryFetchers
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|this
operator|.
name|entryFetchers
argument_list|)
return|;
block|}
block|}
end_class

end_unit

