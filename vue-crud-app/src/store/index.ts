import { createStore } from 'vuex';
import products from './modules/products';
import { RootState } from './types';

// Khởi tạo store
const store = createStore<RootState>({
    modules: {
        products
    }
});

export default store;
