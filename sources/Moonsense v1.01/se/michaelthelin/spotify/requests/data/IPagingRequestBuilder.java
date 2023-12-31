// 
// Decompiled by Procyon v0.5.36
// 

package se.michaelthelin.spotify.requests.data;

import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.IRequest;

public interface IPagingRequestBuilder<T, BT extends IRequest.Builder<Paging<T>, ?>> extends IRequest.Builder<Paging<T>, BT>
{
    BT limit(final Integer p0);
    
    BT offset(final Integer p0);
}
