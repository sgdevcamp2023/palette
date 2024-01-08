/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}",],
  theme: {
    extend: {
      colors: {
        grey: {
          100: '#FCFCFC',
          200: '#E9E9EA',
          300: '#C5C5C5',
          400: '#91989B',
          500: '#474747',
          600: '#141619',
        },
        blueGrey: {
          100: '#E7ECF0',
          200: '#E2E8EB',
          300: '#D9E1E5',
          400: '#CED5DC',
          500: '#BDC5CD',
          600: '#687784',
          700: '#536471',
        },
        green: {
          100: '#58BC6C',
          200: '#00BA7C',
        },
        yellow: {
          100: '#FFE042',
        },
        red: {
          100: '#F91880',
          200: '#CE395F',
        },
        purple: {
          100: '#7856FF',
          200: '#724DBD',
        },
        blue: {
          100: '#E9F5FD',
          200: '#C6E6FB',
          300: '#8ECDF7',
          400: '#4C9EEB',
          500: '#1D9BF0',
        }
      },
    },
  },
  plugins: [],
}

