package com.moxtra.categories;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

/**
 * Created by Angie_Yang on 4/1/2016.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory({Major.class})
public interface Major {
}
