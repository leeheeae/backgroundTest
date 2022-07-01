import {createSlice} from '@reduxjs/toolkit';

export const counter = createSlice({
  name: 'counter',
  initialState: {
    value: 1,
    backgroundTask: false,
  },
  reducers: {
    increment: state => {
      state.value += 1;
    },
    backgroundTaskCheck: (state, action) => {
      state.backgroundTask = action.payload;
    },
  },
});

export const {increment, backgroundTaskCheck} = counter.actions;

export default counter.reducer;
